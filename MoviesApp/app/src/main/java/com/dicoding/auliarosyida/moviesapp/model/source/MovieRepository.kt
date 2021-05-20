package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.NetworkApiResponse
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.RemoteMovieDataSource.LoadMoviesCallback
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData

/**
 *  MovieRepository sebagai filter antara remote dan local
 *  agar apa yang ada di View tidak banyak berubah
 * */
class MovieRepository private constructor(private val remoteMovieDataSource: RemoteMovieDataSource,
                                          private val localMovieDataSource: LocalMovieDataSource,
                                          private val appThreadExecutors: AppThreadExecutors
)  : InterfaceMovieDataSource {

    companion object {
        @Volatile
//        @JvmStatic
        private var instance: MovieRepository? = null

        // filter antara remote dan local
        fun getInstance(remoteData: RemoteMovieDataSource, localData: LocalMovieDataSource, appExecutors: AppThreadExecutors): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    override fun getAllMovies(): LiveData<ResourceWrapData<List<MovieEntity>>> {

        return object : NetworkBoundLocalRemoteResource<List<MovieEntity>, List<MovieResponse>>(appThreadExecutors) {

            public override fun loadFromDB(): LiveData<List<MovieEntity>> =
                    localMovieDataSource.getAllMovies()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
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

    override fun getAllTvShows(): LiveData<ResourceWrapData<List<TvShowEntity>>>  {

        return object : NetworkBoundLocalRemoteResource<List<TvShowEntity>, List<MovieResponse>>(appThreadExecutors) {

            public override fun loadFromDB(): LiveData<List<TvShowEntity>> =
                localMovieDataSource.getAllTvShows()

            override fun shouldFetch(data: List<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<NetworkApiResponse<List<MovieResponse>>> =
                remoteMovieDataSource.getAllTvShows()

            public override fun saveCallResult(data: List<MovieResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in data) {
                    val tvShow = TvShowEntity(response.id,
                        response.poster,
                        response.title,
                        response.quote,
                        response.overview,
                        response.releaseYear,
                        response.genre,
                        response.duration,
                        response.status,
                        response.originalLanguage)
                    tvShowList.add(tvShow)
                }

                localMovieDataSource.insertTvShows(tvShowList)
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

    override fun getDetailTvShow(tvShowId: String): LiveData<ResourceWrapData<TvShowEntity>> {

        return object : NetworkBoundLocalRemoteResource<TvShowEntity, MovieResponse>(appThreadExecutors) {

            public override fun loadFromDB(): LiveData<TvShowEntity> =
                    localMovieDataSource.getDetailTvShow(tvShowId)

            override fun shouldFetch(data : TvShowEntity?): Boolean =
                    data == null

            public override fun createCall(): LiveData<NetworkApiResponse<MovieResponse>> =
                    remoteMovieDataSource.getDetailTvShow(tvShowId)

            override fun saveCallResult(data: MovieResponse) {
                val tvShow = TvShowEntity(data.id,
                        data.poster,
                        data.title,
                        data.quote,
                        data.overview,
                        data.releaseYear,
                        data.genre,
                        data.duration,
                        data.status,
                        data.originalLanguage)
                localMovieDataSource.updateTvShow(tvShow)
            }

        }.asLiveData()
    }

    override fun getFavoritesMovies(): LiveData<List<MovieEntity>> =
            localMovieDataSource.getFavoritedMovies()

    override fun getFavoritesTvShows(): LiveData<List<TvShowEntity>> =
            localMovieDataSource.getFavoritedTvShows()

    override fun setFavoriteMovie(movie: MovieEntity, favState: Boolean) {
        appThreadExecutors.diskIO().execute { localMovieDataSource.setFavoriteMovie(movie, favState) }
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, favState: Boolean) {
        appThreadExecutors.diskIO().execute { localMovieDataSource.setFavoriteTvShow(tvShow, favState) }
    }
}