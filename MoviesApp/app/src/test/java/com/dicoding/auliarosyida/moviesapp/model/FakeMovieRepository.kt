package com.dicoding.auliarosyida.moviesapp.model

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.model.source.InterfaceMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.network.NetworkApiResponse
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

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

            public override fun createCall(): LiveData<NetworkApiResponse<List<MovieResponse>>> =
                    remoteMovieDataSource.getAllMovies()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(response.id,
                            response.poster,
                            response.title,
                            response.quote,
                            response.overview,
                            response.releaseYear,
                            response.genre,
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
                    data == null

            public override fun createCall(): LiveData<NetworkApiResponse<MovieResponse>> =
                    remoteMovieDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(data.id,
                        data.poster,
                        data.title,
                        data.quote,
                        data.overview,
                        data.releaseYear,
                        data.genre,
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