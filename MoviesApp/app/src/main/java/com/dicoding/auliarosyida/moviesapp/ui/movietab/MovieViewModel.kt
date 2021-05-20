package com.dicoding.auliarosyida.moviesapp.ui.movietab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<ResourceWrapData<List<MovieEntity>>> = movieRepository.getAllMovies()
}