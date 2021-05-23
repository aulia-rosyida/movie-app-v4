package com.dicoding.auliarosyida.moviesapp.ui.detailpage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.auliarosyida.moviesapp.model.source.MovieRepository
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.utils.DataMovies
import com.dicoding.auliarosyida.moviesapp.valueobject.ResourceWrapData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {

    private lateinit var viewModel: DetailTvShowViewModel
    private val dummyTvShow = DataMovies.generateTvShows()[0]
    private val tempTvShowId = dummyTvShow.tvShowId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var detailObserver: Observer<ResourceWrapData<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(movieRepository)
        viewModel.setSelectedDetail(tempTvShowId)
    }

    @Test
    fun testGetTvShowEntity() {
        val tvShow = MutableLiveData<ResourceWrapData<TvShowEntity>>()
        tvShow.value = ResourceWrapData.success(dummyTvShow)

        Mockito.`when`(movieRepository.getDetailTvShow(tempTvShowId)).thenReturn(tvShow)
        viewModel.detailTvShow.observeForever(detailObserver)
        Mockito.verify(detailObserver).onChanged(tvShow.value)
    }

    @Test
    fun testSetFavoriteTvShow() {
        val dummyTestTvShow: ResourceWrapData<TvShowEntity> = ResourceWrapData.success(dummyTvShow)
        val tvshow: MutableLiveData<ResourceWrapData<TvShowEntity>> = MutableLiveData<ResourceWrapData<TvShowEntity>>()
        tvshow.value = dummyTestTvShow
        tvshow.value?.data?.favorited = false

        Mockito.`when`(movieRepository.getDetailTvShow(tempTvShowId)).thenReturn(tvshow)
        viewModel.detailTvShow.observeForever(detailObserver)

        dummyTestTvShow.data?.let {
            Mockito.doNothing().`when`(movieRepository).setFavoriteTvShow(it, !it.favorited)
            viewModel.setFavoriteTvShow()
            Mockito.verify(movieRepository).setFavoriteTvShow(it, !it.favorited)
        }
    }

    @Test
    fun testUnFavoriteTvShow() {
        val dummyTestTvShow: ResourceWrapData<TvShowEntity> = ResourceWrapData.success(dummyTvShow)
        val tvshow: MutableLiveData<ResourceWrapData<TvShowEntity>> = MutableLiveData<ResourceWrapData<TvShowEntity>>()
        tvshow.value = dummyTestTvShow
        tvshow.value?.data?.favorited = true

        Mockito.`when`(movieRepository.getDetailTvShow(tempTvShowId)).thenReturn(tvshow)
        viewModel.detailTvShow.observeForever(detailObserver)

        dummyTestTvShow.data?.let {
            Mockito.doNothing().`when`(movieRepository).setFavoriteTvShow(it, !it.favorited)
            viewModel.setFavoriteTvShow()
            Mockito.verify(movieRepository).setFavoriteTvShow(it, !it.favorited)
        }
    }

}