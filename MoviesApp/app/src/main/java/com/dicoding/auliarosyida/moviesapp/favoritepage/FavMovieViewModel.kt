package com.dicoding.auliarosyida.moviesapp.favoritepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase

class FavMovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovie = movieUseCase.getFavoritesMovies().asLiveData()
}