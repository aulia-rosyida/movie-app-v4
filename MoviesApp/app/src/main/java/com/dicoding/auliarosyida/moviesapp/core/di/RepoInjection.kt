package com.dicoding.auliarosyida.moviesapp.core.di

import android.content.Context
import com.dicoding.auliarosyida.moviesapp.core.data.MovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.room.MovieBuilderDatabase
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.network.ApiConfig
import com.dicoding.auliarosyida.moviesapp.core.domain.repository.InterfaceMovieRepository
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieInteractor
import com.dicoding.auliarosyida.moviesapp.core.domain.usecase.MovieUseCase
import com.dicoding.auliarosyida.moviesapp.core.utils.AppThreadExecutors

/**
 * Dengan menggunakan Injection, ViewModelFactory mampu menyediakan kebutuhan MovieRepository.
 * */
object RepoInjection {
    fun provideMovieRepository(context: Context): InterfaceMovieRepository {

        val db = MovieBuilderDatabase.getInstance(context)

        val remoteMovieDataSource = RemoteMovieDataSource.getInstance(ApiConfig.provideApiService())
        val localMovieDataSource = LocalMovieDataSource.getInstance(db.movieDao())
        val appThreadExecutors = AppThreadExecutors()

        return MovieRepository.getInstance(remoteMovieDataSource, localMovieDataSource, appThreadExecutors)
    }

    fun provideMovieUseCase(context: Context): MovieUseCase {
        val repository = provideMovieRepository(context)
        return MovieInteractor(repository)
    }
}