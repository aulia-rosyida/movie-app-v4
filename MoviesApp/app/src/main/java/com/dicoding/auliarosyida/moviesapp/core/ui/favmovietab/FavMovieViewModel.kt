package com.dicoding.auliarosyida.moviesapp.core.ui.favmovietab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity

class FavMovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> = movieRepository.getFavoritesMovies()

    /** tambahkan aksi swipe untuk RecyclerView di BookmarkFragment */
    fun setFavoriteMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.favorited
        movieRepository.setFavoriteMovie(movieEntity, newState)
    }
}