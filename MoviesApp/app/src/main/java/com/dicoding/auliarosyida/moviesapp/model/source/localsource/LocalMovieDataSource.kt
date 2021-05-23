package com.dicoding.auliarosyida.moviesapp.model.source.localsource

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.room.InterfaceMovieDao

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

    fun getAllTvShows(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getTvShows()

    fun insertTvShows(tvShows: List<TvShowEntity>) = mMovieDao.insertTvShows(tvShows)

    fun getDetailTvShow(tvShowId: String): LiveData<TvShowEntity> =
        mMovieDao.getTvShowById(tvShowId)

    fun getFavoritedTvShows(): DataSource.Factory<Int, TvShowEntity> = mMovieDao.getFavoritedTvShows()

    fun setFavoriteTvShow(aShow: TvShowEntity, favState: Boolean) {
        aShow.favorited = favState
        mMovieDao.updateTvShow(aShow)
    }

    fun updateTvShow(aTvShow: TvShowEntity)  = mMovieDao.updateTvShow(aTvShow)

}