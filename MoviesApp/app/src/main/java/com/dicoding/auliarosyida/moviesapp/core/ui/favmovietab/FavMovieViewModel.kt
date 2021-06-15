package com.dicoding.auliarosyida.moviesapp.core.ui.favmovietab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie

class FavMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteMovies(): LiveData<List<Movie>> = movieRepository.getFavoritesMovies()
}