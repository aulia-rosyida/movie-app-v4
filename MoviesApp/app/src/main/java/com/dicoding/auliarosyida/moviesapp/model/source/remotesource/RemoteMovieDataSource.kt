package com.dicoding.auliarosyida.moviesapp.model.source.remotesource

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.utils.IdlingResourceEspresso
import com.dicoding.auliarosyida.moviesapp.utils.JsonResponseHelper

class RemoteMovieDataSource private constructor(private val jsonResponseHelper: JsonResponseHelper) {

    private val handlerLooper = Handler(Looper.getMainLooper())

    companion object {
        private const val serviceLatencyInMillis: Long = 2000

        @Volatile
        private var instance: RemoteMovieDataSource? = null

        fun getInstance(helper: JsonResponseHelper): RemoteMovieDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteMovieDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies() : LiveData<NetworkApiResponse<List<MovieResponse>>> {

        IdlingResourceEspresso.increment()
        val resultMovie = MutableLiveData<NetworkApiResponse<List<MovieResponse>>>()

        handlerLooper.postDelayed({
            resultMovie.value = NetworkApiResponse.success(jsonResponseHelper.loadMovies())
            IdlingResourceEspresso.decrement()
         }, serviceLatencyInMillis)

        return resultMovie
    }

    fun getAllTvShows() : LiveData<NetworkApiResponse<List<MovieResponse>>> {

        IdlingResourceEspresso.increment()
        val resultTvShow = MutableLiveData<NetworkApiResponse<List<MovieResponse>>>()

        handlerLooper.postDelayed({
            resultTvShow.value = NetworkApiResponse.success(jsonResponseHelper.loadTvShows())
            IdlingResourceEspresso.decrement()
        }, serviceLatencyInMillis)

        return resultTvShow
    }

    fun getDetailMovie(movieId: String): LiveData<NetworkApiResponse<MovieResponse>> {

        IdlingResourceEspresso.increment()
        val resultDetailMovie = MutableLiveData<NetworkApiResponse<MovieResponse>>()

        handlerLooper.postDelayed({
            resultDetailMovie.value = NetworkApiResponse.success(jsonResponseHelper.loadMovie(movieId))
            IdlingResourceEspresso.decrement()
        }, serviceLatencyInMillis)

        return resultDetailMovie
    }

    fun getDetailTvShow(tvShowId: String): LiveData<NetworkApiResponse<MovieResponse>> {

        IdlingResourceEspresso.increment()
        val resultDetailTvShow = MutableLiveData<NetworkApiResponse<MovieResponse>>()

        handlerLooper.postDelayed({
            resultDetailTvShow.value = NetworkApiResponse.success(jsonResponseHelper.loadTvShow(tvShowId))
            IdlingResourceEspresso.decrement()
        }, serviceLatencyInMillis)

        return resultDetailTvShow

    }

    interface LoadMoviesCallback {
        fun onAllMoviesReceived(movieResponses: List<MovieResponse>)
    }
}


