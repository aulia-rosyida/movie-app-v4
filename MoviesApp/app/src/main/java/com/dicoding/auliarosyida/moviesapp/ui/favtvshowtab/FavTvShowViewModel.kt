package com.dicoding.auliarosyida.moviesapp.ui.favtvshowtab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity

class FavTvShowViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> = movieRepository.getFavoritesTvShows()

    /** tambahkan aksi swipe untuk RecyclerView di BookmarkFragment */
    fun setFavoriteMovie(tvShowEntity: TvShowEntity) {
        val newState = !tvShowEntity.favorited
        movieRepository.setFavoriteTvShow(tvShowEntity, newState)
    }
}