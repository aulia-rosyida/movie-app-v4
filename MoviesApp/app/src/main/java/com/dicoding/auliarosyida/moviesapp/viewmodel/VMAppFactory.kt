package com.dicoding.auliarosyida.moviesapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.auliarosyida.moviesapp.core.di.RepoInjection
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.ui.detailpage.DetailMovieViewModel
import com.dicoding.auliarosyida.moviesapp.core.ui.favmovietab.FavMovieViewModel
import com.dicoding.auliarosyida.moviesapp.core.ui.movietab.MovieViewModel

class VMAppFactory private constructor(private val mMovieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: VMAppFactory? = null

        fun getInstance(context: Context): VMAppFactory =
            instance ?: synchronized(this) {
                instance ?: VMAppFactory(RepoInjection.provideMovieRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavMovieViewModel::class.java) -> {
                FavMovieViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}