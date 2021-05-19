package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.lifecycle.LiveData
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

interface InterfaceMovieDataSource {
    fun getAllMovies(): LiveData<ResourceWrapData<List<MovieResponse>>>

    fun getAllTvShows(): LiveData<ResourceWrapData<List<MovieResponse>>>

    fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<MovieResponse>>

    fun getDetailTvShow(tvShowId: String): LiveData<ResourceWrapData<MovieResponse>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)

    fun setFavoriteTvShow(movie: TvShowEntity, state: Boolean)
}