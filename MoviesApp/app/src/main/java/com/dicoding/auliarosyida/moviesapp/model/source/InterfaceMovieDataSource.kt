package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

interface InterfaceMovieDataSource {
    fun getAllMovies(): LiveData<ResourceWrapData<PagedList<MovieEntity>>>

    fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<MovieEntity>>

    fun getFavoritesMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, favState: Boolean)
}