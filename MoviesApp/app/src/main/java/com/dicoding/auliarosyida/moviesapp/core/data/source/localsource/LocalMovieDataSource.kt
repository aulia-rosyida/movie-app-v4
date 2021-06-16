package com.dicoding.auliarosyida.moviesapp.core.data.source.localsource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.room.InterfaceMovieDao

class LocalMovieDataSource private constructor(private val mMovieDao: InterfaceMovieDao) {

    companion object {
        private var INSTANCE: LocalMovieDataSource? = null

        fun getInstance(academyDao: InterfaceMovieDao): LocalMovieDataSource =
            INSTANCE ?: LocalMovieDataSource(academyDao)
    }

    fun getAllMovies(): LiveData<List<MovieEntity>> = mMovieDao.getMovies()

    fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    fun getDetailMovie(movieId: String): LiveData<MovieEntity> =
        mMovieDao.getMovieById(movieId)

    fun getFavoritedMovies(): LiveData<List<MovieEntity>> = mMovieDao.getFavoritedMovies()

    fun setFavoriteMovie(aMovie: MovieEntity, favState: Boolean) {
        aMovie.favorited = favState
        Log.d("LocalMovieDataSource", "ini state yg diubah ${aMovie.favorited}")
        mMovieDao.updateMovie(aMovie)
    }

    fun updateMovie(aMovie: MovieEntity) = mMovieDao.updateMovie(aMovie)
}