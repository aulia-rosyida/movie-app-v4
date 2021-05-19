package com.dicoding.auliarosyida.moviesapp.model.source.localsource

import androidx.lifecycle.LiveData
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.room.InterfaceMovieDao

class LocalMovieDataSource private constructor(private val mMovieDao: InterfaceMovieDao) {

    companion object {
        private var INSTANCE: LocalMovieDataSource? = null

        fun getInstance(academyDao: InterfaceMovieDao): LocalMovieDataSource =
            INSTANCE ?: LocalMovieDataSource(academyDao)
    }

    fun getAllMovies(): LiveData<List<MovieEntity>> = mMovieDao.getMovies()

    fun getFavoritedMovies(): LiveData<List<MovieEntity>> = mMovieDao.getFavoritedMovies()

    fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    fun setFavoriteMovie(aMovie: MovieEntity, favState: Boolean) {
        aMovie.favorited = favState
        mMovieDao.updateMovie(aMovie)
    }

    fun getAllTvShows(): LiveData<List<TvShowEntity>> = mMovieDao.getTvShows()

    fun getFavoritedTvShows(): LiveData<List<TvShowEntity>> = mMovieDao.getFavoritedTvShows()

    fun insertTvShows(tvShows: List<TvShowEntity>) = mMovieDao.insertTvShows(tvShows)

    fun setFavoriteTvShow(aShow: TvShowEntity, favState: Boolean) {
        aShow.favorited = favState
        mMovieDao.updateTvShow(aShow)
    }

}