package com.dicoding.auliarosyida.moviesapp.model.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.dicoding.auliarosyida.moviesapp.model.FakeMovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.LocalMovieDataSource
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.RemoteMovieDataSource
import com.dicoding.auliarosyida.moviesapp.utils.AppThreadExecutors
import com.dicoding.auliarosyida.moviesapp.utils.AppThreadExecutorsTest
import com.dicoding.auliarosyida.moviesapp.utils.DataMovies
import com.dicoding.auliarosyida.moviesapp.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteMovieDataSource::class.java)
    private val local = mock(LocalMovieDataSource::class.java)
    private val appExecutors = mock(AppThreadExecutors::class.java)
    private val testExecutors : AppThreadExecutors = AppThreadExecutors(AppThreadExecutorsTest(), AppThreadExecutorsTest(), AppThreadExecutorsTest())

    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)

    private val movieResponses = DataMovies.generateRemoteDummyMovies()
    private val movieId = movieResponses[0].id

    private val tvShowResponses = DataMovies.generateRemoteDummyTvShows()
    private val tvShowId = tvShowResponses[0].id

    @Test
    fun testGetAllMovies() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = DataMovies.generateMovies()
        `when`(local.getAllMovies()).thenReturn(dummyMovies)

        val movieEntities = LiveDataTestUtil.getValue(movieRepository.getAllMovies())
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun testGetAllTvShows() {
        val dummyTvShows = MutableLiveData<List<TvShowEntity>>()
        dummyTvShows.value = DataMovies.generateTvShows()
        `when`(local.getAllTvShows()).thenReturn(dummyTvShows)

        val tvShowEntities = LiveDataTestUtil.getValue(movieRepository.getAllTvShows())
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun testGetDetailMovie() {
        val dummyDetailMovie = MutableLiveData<MovieEntity>()
        dummyDetailMovie.value = DataMovies.generateDummyMovie(movieId)
        `when`(local.getDetailMovie(movieId)).thenReturn(dummyDetailMovie)

        val resultMovie = LiveDataTestUtil.getValue(movieRepository.getDetailMovie(movieId))
        verify(local).getDetailMovie(movieId)
        assertNotNull(resultMovie.data)
        assertEquals(movieResponses[0].genre, resultMovie.data?.genre)
    }

    @Test
    fun testGetDetailTvShow() {
        val dummyDetailTvShow = MutableLiveData<TvShowEntity>()
        dummyDetailTvShow.value = DataMovies.generateDummyTvShow(tvShowId)
        `when`(local.getDetailTvShow(tvShowId)).thenReturn(dummyDetailTvShow)

        val resultTvShow = LiveDataTestUtil.getValue(movieRepository.getDetailTvShow(tvShowId))
        verify(local).getDetailTvShow(tvShowId)
        assertNotNull(resultTvShow.data)
        assertEquals(tvShowResponses[0].genre, resultTvShow.data?.genre)
    }

    @Test
    fun testGetFavoritesMovies() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = DataMovies.generateMovies()
        `when`(local.getFavoritedMovies()).thenReturn(dummyMovies)

        val movieEntities = LiveDataTestUtil.getValue(movieRepository.getFavoritesMovies())
        verify(local).getFavoritedMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun testGetFavoritesTvShows() {
        val dummyTvShows = MutableLiveData<List<TvShowEntity>>()
        dummyTvShows.value = DataMovies.generateTvShows()
        `when`(local.getFavoritedTvShows()).thenReturn(dummyTvShows)

        val tvShowEntities = LiveDataTestUtil.getValue(movieRepository.getFavoritesTvShows())
        verify(local).getFavoritedTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.size.toLong())
    }

    @Test
    fun testSetFavoritesMovies() {
        val dataMovieDummy = DataMovies.generateMovies()[0].copy(favorited = false)
        val newFavState: Boolean = !dataMovieDummy.favorited

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteMovie(dataMovieDummy, newFavState)

        movieRepository.setFavoriteMovie(dataMovieDummy, newFavState)
        verify(local, times(1)).setFavoriteMovie(dataMovieDummy, newFavState)
    }

    @Test
    fun testSetUnFavoritesMovies() {
        val dataMovieDummy = DataMovies.generateMovies()[0].copy(favorited = true)
        val newFavState: Boolean = !dataMovieDummy.favorited

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteMovie(dataMovieDummy, newFavState)

        movieRepository.setFavoriteMovie(dataMovieDummy, newFavState)
        verify(local, times(1)).setFavoriteMovie(dataMovieDummy, newFavState)
    }

    @Test
    fun testSetFavoritesTvShows() {
        val dataTvShowDummy = DataMovies.generateTvShows()[0].copy(favorited = false)
        val newFavState: Boolean = !dataTvShowDummy.favorited

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteTvShow(dataTvShowDummy, newFavState)

        movieRepository.setFavoriteTvShow(dataTvShowDummy, newFavState)
        verify(local, times(1)).setFavoriteTvShow(dataTvShowDummy, newFavState)
    }

    @Test
    fun testSetUnFavoritesTvShows() {
        val dataTvShowDummy = DataMovies.generateTvShows()[0].copy(favorited = true)
        val newFavState: Boolean = !dataTvShowDummy.favorited

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setFavoriteTvShow(dataTvShowDummy, newFavState)

        movieRepository.setFavoriteTvShow(dataTvShowDummy, newFavState)
        verify(local, times(1)).setFavoriteTvShow(dataTvShowDummy, newFavState)
    }
}