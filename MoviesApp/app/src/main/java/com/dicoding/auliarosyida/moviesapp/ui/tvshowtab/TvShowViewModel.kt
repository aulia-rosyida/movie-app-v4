package com.dicoding.auliarosyida.moviesapp.ui.tvshowtab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class TvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getTvShows(): LiveData<ResourceWrapData<List<TvShowEntity>>> = movieRepository.getAllTvShows()
}