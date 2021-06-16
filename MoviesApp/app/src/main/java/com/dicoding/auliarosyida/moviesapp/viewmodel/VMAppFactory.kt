package com.dicoding.auliarosyida.moviesapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.auliarosyida.moviesapp.core.di.RepoInjection
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase
import com.dicoding.auliarosyida.moviesapp.core.ui.detailpage.DetailMovieViewModel
import com.dicoding.auliarosyida.moviesapp.core.ui.favmovietab.FavMovieViewModel
import com.dicoding.auliarosyida.moviesapp.core.ui.movietab.MovieViewModel

class VMAppFactory private constructor(private val movieUseCase: MovieUseCase) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: VMAppFactory? = null

        fun getInstance(context: Context): VMAppFactory =
            instance ?: synchronized(this) {
                instance ?: VMAppFactory(RepoInjection.provideMovieUseCase(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                DetailMovieViewModel(movieUseCase) as T
            }
            modelClass.isAssignableFrom(FavMovieViewModel::class.java) -> {
                FavMovieViewModel(movieUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}