package com.dicoding.auliarosyida.academy.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.auliarosyida.academy.data.source.remote.ApiResponse
import com.dicoding.auliarosyida.academy.data.source.remote.StatusResponse
import com.dicoding.auliarosyida.academy.utils.AppExecutors
import com.dicoding.auliarosyida.academy.vo.Resource

/**
 * kelas NetworkBoundResource, Anda bisa memakainya di AcademyRepository untuk menyimpan data RemoteDataSource menjadi LocalDataSource
 *
 * NetworkBoundResource akan bekerja jika LocalDataSource tidak menyediakan data,
 * ia akan otomatis melakukan request ke RemoteDataSource dan akan melakukan insert data
 * */
abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected fun onFetchFailed() {}

    /**
     * punyai 4 komponen utama, yakni :
     * loadFromDB() yang berfungsi untuk mengakses data dari local dataabse,
     * shouldFetch untuk mengetahui apakah perlu akses remote database atau tidak,
     * createCall untuk mengakses remote database
     * saveCallResult untuk menyimpan data hasil dari remote database ke local database.
     * */
    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                StatusResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Resource.success(newData)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}