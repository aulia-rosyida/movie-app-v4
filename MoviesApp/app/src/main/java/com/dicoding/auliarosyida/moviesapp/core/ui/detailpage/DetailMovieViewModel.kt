package com.dicoding.auliarosyida.moviesapp.core.ui.detailpage

import android.util.Log
import androidx.lifecycle.*
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.StatusData
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData
import kotlinx.coroutines.runBlocking

class DetailMovieViewModel(private val movieUseCase: MovieUseCase): ViewModel() {
    private val detailId = MutableLiveData<String>()

    fun setSelectedDetail(detailId: String) {
        this.detailId.value = detailId
    }

    var detailMovie: LiveData<StatusData<Movie>> = Transformations.switchMap(detailId) { mDetailId ->
        detailId.value.let { movieUseCase.getDetailMovie(mDetailId).asLiveData() }
    }

    fun setFavoriteMovie() {
        val movieResource = detailMovie.value
        if (movieResource != null) {
            val movieDomain = movieResource.data
            if (movieDomain != null) {
                val newState = !movieDomain.favorited
                runBlocking{
                    movieUseCase.setFavoriteMovie(movieDomain, newState)
                }
            }
        }
    }
}