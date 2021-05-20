package com.dicoding.auliarosyida.moviesapp.ui.detailpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class DetailMovieViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val detailId = MutableLiveData<String>()

    fun setSelectedDetail(detailId: String) {
        this.detailId.value = detailId
    }

    var detailMovie: LiveData<ResourceWrapData<MovieEntity>> = Transformations.switchMap(detailId) { mDetailId ->
        detailId.value?.let { movieRepository.getDetailMovie(mDetailId) }
    }

    fun setFavoriteMovie() {
        val movieResource = detailMovie.value
        if (movieResource != null) {
            val movieEntity = movieResource.data
            if (movieEntity != null) {
                val newState = !movieEntity.favorited
                movieRepository.setFavoriteMovie(movieEntity, newState)
            }
        }
    }
}