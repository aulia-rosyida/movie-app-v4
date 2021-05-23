package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

interface InterfaceMovieDataSource {
    fun getAllMovies(): LiveData<ResourceWrapData<PagedList<MovieEntity>>>

    fun getAllTvShows(): LiveData<ResourceWrapData<PagedList<TvShowEntity>>>

    fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<MovieEntity>>

    fun getDetailTvShow(tvShowId: String): LiveData<ResourceWrapData<TvShowEntity>>

    fun getFavoritesMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoritesTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteMovie(movie: MovieEntity, favState: Boolean)

    fun setFavoriteTvShow(tvShow: TvShowEntity, favState: Boolean)
}