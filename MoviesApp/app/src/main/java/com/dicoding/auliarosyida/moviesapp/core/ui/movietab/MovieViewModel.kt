package com.dicoding.auliarosyida.moviesapp.core.ui.movietab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class MovieViewModel(movieUseCase: MovieUseCase) : ViewModel() {

    val movie = movieUseCase.getAllMovies().asLiveData()
}