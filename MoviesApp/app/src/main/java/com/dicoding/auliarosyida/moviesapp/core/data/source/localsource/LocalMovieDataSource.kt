package com.dicoding.auliarosyida.moviesapp.core.data.source.localsource

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

    fun getAllMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getMovies()

    fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    fun getDetailMovie(movieId: String): LiveData<MovieEntity> =
        mMovieDao.getMovieById(movieId)

    fun getFavoritedMovies(): DataSource.Factory<Int, MovieEntity> = mMovieDao.getFavoritedMovies()

    fun setFavoriteMovie(aMovie: MovieEntity, favState: Boolean) {
        aMovie.favorited = favState
        mMovieDao.updateMovie(aMovie)
    }

    fun updateMovie(aMovie: MovieEntity) = mMovieDao.updateMovie(aMovie)
}