package com.dicoding.auliarosyida.moviesapp.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import com.dicoding.auliarosyida.moviesapp.core.domain.repository.InterfaceMovieRepository
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.network.ApiResponse
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.core.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.core.utils.DataMapperHelper
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData
import java.util.*

/**
 *  MovieRepository sebagai filter antara remote dan local
 *  agar apa yang ada di View tidak banyak berubah
 * */
class MovieRepository private constructor(private val remoteMovieDataSource: RemoteMovieDataSource,
                                          private val localMovieDataSource: LocalMovieDataSource,
                                          private val appThreadExecutors: AppThreadExecutors
)  : InterfaceMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        // filter antara remote dan local
        fun getInstance(remoteData: RemoteMovieDataSource, localData: LocalMovieDataSource, appExecutors: AppThreadExecutors): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    override fun getAllMovies(): LiveData<ResourceWrapData<List<Movie>>> {

        return object : NetworkBoundLocalRemoteResource<List<Movie>, List<MovieResponse>>(appThreadExecutors) {

            override fun loadFromDB(): LiveData<List<Movie>> {
                return Transformations.map(localMovieDataSource.getAllMovies()) {
                    DataMapperHelper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                    remoteMovieDataSource.getAllMovies()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val movieEntities = DataMapperHelper.mapResponsesToEntities(data)
                localMovieDataSource.insertMovies(movieEntities)
            }
        }.asLiveData()
    }

    override fun getDetailMovie(movieId: String): LiveData<ResourceWrapData<Movie>> {

        return object : NetworkBoundLocalRemoteResource<Movie, MovieResponse>(appThreadExecutors) {

            public override fun loadFromDB(): LiveData<Movie> {
                return Transformations.map(localMovieDataSource.getDetailMovie(movieId)) {
                    var tempArrayEntity = ArrayList<MovieEntity>()
                    tempArrayEntity.add(it)
                    val movieDomain = DataMapperHelper.mapEntitiesToDomain(tempArrayEntity)
                    movieDomain[0]
                }
            }

            override fun shouldFetch(data : Movie?): Boolean =
                    data == null || data.title == data.quote || data.duration == null

            public override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteMovieDataSource.getDetailMovie(movieId)

            override fun saveCallResult(data: MovieResponse) {
                var tempArrayResponse = ArrayList<MovieResponse>()
                tempArrayResponse.add(data)
                val movieEntities = DataMapperHelper.mapResponsesToEntities(tempArrayResponse)
                localMovieDataSource.updateMovie(movieEntities[0])
            }

        }.asLiveData()
    }

    override fun getFavoritesMovies(): LiveData<List<Movie>> {
        return Transformations.map(localMovieDataSource.getFavoritedMovies()) {
            DataMapperHelper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovie(movie: Movie, favState: Boolean) {
        val tourismEntity = DataMapperHelper.mapDomainToEntity(movie)
        appThreadExecutors.diskIO().execute { localMovieDataSource.setFavoriteMovie(tourismEntity, favState) }
    }
}