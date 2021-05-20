package com.dicoding.auliarosyida.moviesapp.ui.detailpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

class DetailTvShowViewModel(private val movieRepository: MovieRepository): ViewModel() {
    private val detailId = MutableLiveData<String>()

    fun setSelectedDetail(detailId: String) {
        this.detailId.value = detailId
    }

    var detailTvShow: LiveData<ResourceWrapData<TvShowEntity>> = Transformations.switchMap(detailId) { mDetailId ->
        detailId.value?.let { movieRepository.getDetailTvShow(mDetailId) }
    }

    fun setFavoriteTvShow() {
        val tvShowResource = detailTvShow.value
        if (tvShowResource != null) {
            val tvShowEntity = tvShowResource.data
            if (tvShowEntity != null) {
                val newState = !tvShowEntity.favorited
                movieRepository.setFavoriteTvShow(tvShowEntity, newState)
            }
        }
    }
}