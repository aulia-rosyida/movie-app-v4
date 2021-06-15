package com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.network.ApiResponse
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.network.ApiService
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.response.ListMovieResponse
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.response.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteMovieDataSource private constructor(private val apiService: ApiService) {

    private val handlerLooper = Handler(Looper.getMainLooper())

    companion object {
        private const val serviceLatencyInMillis: Long = 2000

        @Volatile
        private var instance: RemoteMovieDataSource? = null

        fun getInstance(service: ApiService): RemoteMovieDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteMovieDataSource(service)
            }
    }

    fun getAllMovies() : LiveData<ApiResponse<List<MovieResponse>>> {

        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()

        //get data from remote api
        val client = apiService.getList()

        client.enqueue(object : Callback<ListMovieResponse> {
            override fun onResponse(
                call: Call<ListMovieResponse>,
                response: Response<ListMovieResponse>
            ) {
                val dataArray = response.body()?.movies
                resultMovie.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<ListMovieResponse>, t: Throwable) {
                resultMovie.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteAllDataSource", t.message.toString())
            }
        })

        return resultMovie
    }

    fun getDetailMovie(movieId: String): LiveData<ApiResponse<MovieResponse>> {

        val resultDetailMovie = MutableLiveData<ApiResponse<MovieResponse>>()

        //get data from remote api
        val client = apiService.getDetail(movieId)

        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                val dataArray = response.body()
                Log.d("REMOTE SOURCE", "DETAILLLL : $dataArray")
                resultDetailMovie.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                resultDetailMovie.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDetailDataSource", t.message.toString())
            }
        })

        return resultDetailMovie
    }
}


