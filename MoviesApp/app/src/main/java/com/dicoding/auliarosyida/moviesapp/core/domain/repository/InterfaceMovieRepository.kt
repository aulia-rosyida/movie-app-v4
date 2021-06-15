package com.dicoding.auliarosyida.moviesapp.core.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

interface InterfaceMovieRepository {
    fun getAllMovies(): LiveData<ResourceWrapData<List<Movie>>>

    fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<Movie>>

    fun getFavoritesMovies(): LiveData<List<Movie>>

    fun setFavoriteMovie(movie: Movie, favState: Boolean)
}