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

            public override fun saveCallResult(courseResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in courseResponses) {
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

            public override fun saveCallResult(courseResponses: List<MovieResponse>) {
                val tvShowList = ArrayList<TvShowEntity>()
                for (response in courseResponses) {
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

            override fun shouldFetch(movie : MovieEntity?): Boolean =
                movie == null

            public override fun createCall(): LiveData<NetworkApiResponse<List<MovieResponse>>> =
                remoteMovieDataSource.getAllMovies()

            public override fun saveCallResult(courseResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in courseResponses) {
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


//        val detailMovieResult = MutableLiveData<MovieResponse>()
//
//        remoteMovieDataSource.getAllMovies(object : LoadMoviesCallback {
//            override fun onAllMoviesReceived(movieResponses: List<MovieResponse>) {
//                lateinit var aMovie: MovieResponse
//                for (response in movieResponses) {
//                    if (response.id == movieId) {
//                        aMovie = response
//                    }
//                }
//                detailMovieResult.postValue(aMovie)
//            }
//        })
//        return detailMovieResult
    }

    override fun getDetailTvShow(tvShowId: String): LiveData<MovieResponse> {
        val detailTvShowResult = MutableLiveData<MovieResponse>()

        remoteMovieDataSource.getAllTvShows(object : LoadMoviesCallback {
            override fun onAllMoviesReceived(movieResponses: List<MovieResponse>) {
                lateinit var aTvShow: MovieResponse
                for (response in movieResponses) {
                    if (response.id == tvShowId) {
                        aTvShow = response
                    }
                }
                detailTvShowResult.postValue(aTvShow)
            }
        })
        return detailTvShowResult
    }
}