package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.bumptech.glide.load.engine.Resource
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.NetworkApiResponse
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.StatusNetworkResponse
import com.dicoding.auliarosyida.moviesapp.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

abstract class NetworkBoundLocalRemoteResource <ResultType, RequestType>(private val mExecutors: AppThreadExecutors) {

    private val result = MediatorLiveData<ResourceWrapData<ResultType>>()

    init {
        result.value = ResourceWrapData.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = ResourceWrapData.success(newData)
                }
            }
        }
    }

    protected fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<NetworkApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = ResourceWrapData.loading(newData)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusNetworkResponse.SUCCESS ->
                    mExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = ResourceWrapData.success(newData)
                            }
                        }
                    }
                StatusNetworkResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = ResourceWrapData.success(newData)
                    }
                }
                StatusNetworkResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = ResourceWrapData.error(response.message, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<ResourceWrapData<ResultType>> = result
}