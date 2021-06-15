package com.dicoding.auliarosyida.moviesapp.core.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.data.source.InterfaceMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.network.NetworkApiResponse
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.network.ApiResponse
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.core.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData
import java.util.*

/**
 *  MovieRepository sebagai filter antara remote dan local
 *  agar apa yang ada di View tidak banyak berubah
 * */
class FakeMovieRepository (private val remoteMovieDataSource: RemoteMovieDataSource,
                           private val localMovieDataSource: LocalMovieDataSource,
                           private val appThreadExecutors: AppThreadExecutors) :
    InterfaceMovieDataSource {

    override fun getAllMovies(): LiveData<ResourceWrapData<PagedList<MovieEntity>>> {

        return object : NetworkBoundLocalRemoteResource<PagedList<MovieEntity>, List<MovieResponse>>(appThreadExecutors) {

            public override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localMovieDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteMovieDataSource.getAllMovies()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(response.id,
                        response.poster,
                        response.title,
                        response.title,
                        response.overview,
                        response.releaseYear,
                        "",
                        response.duration,
                        response.status,
                        response.originalLanguage)
                    movieList.add(movie)
                }

                localMovieDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<MovieEntity>> {

        return object : NetworkBoundLocalRemoteResource<MovieEntity, MovieResponse>(appThreadExecutors) {

            public override fun loadFromDB(): LiveData<MovieEntity> =
                localMovieDataSource.getDetailMovie(movieId)

            override fun shouldFetch(data : MovieEntity?): Boolean =
                data == null || data.title == data.quote || data.duration == null

            public override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteMovieDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: MovieResponse) {
                val genreBuilder = StringBuilder()
                data.genreIds.forEachIndexed { idx, g ->
                    if(idx == 0) genreBuilder.append(g.name)
                    else genreBuilder.append(", ${g.name}")
                }
                var tempQuote =
                    if (data.quote.isEmpty()) data.title
                    else data.quote

                val movie = MovieEntity(data.id,
                    data.poster,
                    data.title,
                    tempQuote,
                    data.overview,
                    data.releaseYear,
                    genreBuilder.toString(),
                    data.duration,
                    data.status,
                    data.originalLanguage)
                localMovieDataSource.updateMovie(movie)
            }

        }.asLiveData()
    }

    override fun getFavoritesMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localMovieDataSource.getFavoritedMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, favState: Boolean) {
        appThreadExecutors.diskIO().execute { localMovieDataSource.setFavoriteMovie(movie, favState) }
    }
}