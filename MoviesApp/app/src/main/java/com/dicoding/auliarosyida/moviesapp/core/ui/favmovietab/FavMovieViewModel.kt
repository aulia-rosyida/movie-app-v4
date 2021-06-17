package com.dicoding.auliarosyida.moviesapp.core.ui.favmovietab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase

class FavMovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val favoriteMovie = movieUseCase.getFavoritesMovies().asLiveData()
}