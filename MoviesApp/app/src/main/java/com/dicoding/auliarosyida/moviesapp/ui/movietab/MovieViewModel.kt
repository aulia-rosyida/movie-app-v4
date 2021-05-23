package com.dicoding.auliarosyida.moviesapp.ui.movietab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getMovies(): LiveData<ResourceWrapData<PagedList<MovieEntity>>> = movieRepository.getAllMovies()
}