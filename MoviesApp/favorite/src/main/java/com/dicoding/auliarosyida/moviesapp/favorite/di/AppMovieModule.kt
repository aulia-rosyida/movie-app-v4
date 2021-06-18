package com.dicoding.auliarosyida.moviesapp.di

import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieInteractor
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase
import com.dicoding.auliarosyida.moviesapp.detailpage.DetailMovieViewModel
import com.dicoding.auliarosyida.moviesapp.favoritepage.FavMovieViewModel
import com.dicoding.auliarosyida.moviesapp.moviepage.MovieViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel

val favModule = module {
    viewModel { FavMovieViewModel(get()) }
}