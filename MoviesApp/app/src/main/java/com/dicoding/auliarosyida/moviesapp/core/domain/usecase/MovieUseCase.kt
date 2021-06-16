package com.dicoding.auliarosyida.moviesapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

interface MovieUseCase {
    fun getAllMovies(): LiveData<ResourceWrapData<List<Movie>>>
    fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<Movie>>
    fun getFavoritesMovies(): LiveData<List<Movie>>
    fun setFavoriteMovie(movie: Movie, state: Boolean)
}