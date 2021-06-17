package com.dicoding.auliarosyida.moviesapp.core.domain.usecase

import androidx.lifecycle.LiveData
import com.dicoding.auliarosyida.moviesapp.core.data.StatusData
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getAllMovies(): Flow<StatusData<List<Movie>>>
    fun getDetailMovie(movieId: String): Flow<StatusData<Movie>>
    fun getFavoritesMovies(): Flow<List<Movie>>
    suspend fun setFavoriteMovie(movie: Movie, state: Boolean)
}