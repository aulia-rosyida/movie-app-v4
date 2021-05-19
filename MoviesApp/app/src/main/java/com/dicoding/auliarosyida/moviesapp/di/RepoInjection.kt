package com.dicoding.auliarosyida.moviesapp.di

import android.content.Context
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.room.MovieBuilderDatabase
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.utils.JsonResponseHelper

/**
 * Dengan menggunakan Injection, ViewModelFactory mampu menyediakan kebutuhan MovieRepository.
 * */
object RepoInjection {
    fun provideMovieRepository(context: Context): MovieRepository {

        val db = MovieBuilderDatabase.getInstance(context)

        val remoteMovieDataSource = RemoteMovieDataSource.getInstance(JsonResponseHelper(context))
        val localMovieDataSource = LocalMovieDataSource.getInstance(db.movieDao())
        val appThreadExecutors = AppThreadExecutors()

        return MovieRepository.getInstance(remoteMovieDataSource, localMovieDataSource, appThreadExecutors)
    }
}