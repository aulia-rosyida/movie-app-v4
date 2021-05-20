package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.lifecycle.LiveData
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

interface InterfaceMovieDataSource {
    fun getAllMovies(): LiveData<ResourceWrapData<List<MovieEntity>>>

    fun getAllTvShows(): LiveData<ResourceWrapData<List<TvShowEntity>>>

    fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<MovieEntity>>

    fun getDetailTvShow(tvShowId: String): LiveData<ResourceWrapData<TvShowEntity>>

    fun getFavoritesMovies(): LiveData<List<MovieEntity>>

    fun getFavoritesTvShows(): LiveData<List<TvShowEntity>>

    fun setFavoriteMovie(movie: MovieEntity, favState: Boolean)

    fun setFavoriteTvShow(tvShow: TvShowEntity, favState: Boolean)
}