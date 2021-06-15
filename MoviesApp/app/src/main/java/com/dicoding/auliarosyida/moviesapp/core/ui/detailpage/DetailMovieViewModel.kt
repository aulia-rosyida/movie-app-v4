package com.dicoding.auliarosyida.moviesapp.core.ui.detailpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class DetailMovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val detailId = MutableLiveData<String>()

    fun setSelectedDetail(detailId: String) {
        this.detailId.value = detailId
    }

    var detailMovie: LiveData<ResourceWrapData<Movie>> = Transformations.switchMap(detailId) { mDetailId ->
        detailId.value?.let { movieRepository.getDetailMovie(mDetailId) }
    }

    fun setFavoriteMovie(movie: Movie, state: Boolean) {
        movieRepository.setFavoriteMovie(movie, state)
    }
}