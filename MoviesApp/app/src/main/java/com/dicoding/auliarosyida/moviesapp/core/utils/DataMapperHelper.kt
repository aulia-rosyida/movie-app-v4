package com.dicoding.auliarosyida.moviesapp.core.utils

import android.util.Log
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie

object DataMapperHelper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()

        input.map {
            val genreBuilder = StringBuilder()
//            try{
//                it.genreIds.forEachIndexed { idx, g ->
//                    if(idx == 0) genreBuilder.append("${g.name}")
//                    else genreBuilder.append(", ${g.name}")
//                }
//            } catch(e: Exception) {
//                Log.d("data mapper exception", "- ${e.printStackTrace()}")
//            }

            var tempQuote = it.title
//                if (it.quote.length < 2) it.title
//                else it.quote

            val movie = MovieEntity(
                movieId = it.id,
                poster = it.poster,
                title = it.title,
                quote = tempQuote,
                overview = it.overview,
                releaseYear = it.releaseYear,
                genre = genreBuilder.toString(),
                duration = it.duration,
                status = it.status,
                originalLanguage = it.originalLanguage,
                favorited = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.movieId,
                poster = it.poster,
                title = it.title,
                quote = it.quote,
                overview = it.overview,
                releaseYear = it.releaseYear,
                genre = it.genre,
                duration = it.duration,
                status = it.status,
                originalLanguage = it.originalLanguage,
                favorited = it.favorited
            )
        }

    fun mapDomainToEntity(input: Movie) = MovieEntity(
        movieId = input.id,
        poster = input.poster,
        title = input.title,
        quote = input.quote,
        overview = input.overview,
        releaseYear = input.releaseYear,
        genre = input.genre,
        duration = input.duration,
        status = input.status,
        originalLanguage = input.originalLanguage,
        favorited = input.favorited
    )
}