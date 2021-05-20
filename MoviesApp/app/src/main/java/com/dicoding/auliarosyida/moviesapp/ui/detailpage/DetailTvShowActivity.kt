package com.dicoding.auliarosyida.moviesapp.ui.detailpage

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.databinding.ActivityDetailBinding
import com.dicoding.auliarosyida.moviesapp.databinding.ContentDetailMovieBinding
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.valueobject.IndicatorStatus
import com.dicoding.auliarosyida.moviesapp.viewmodel.VMAppFactory

class DetailTvShowActivity: AppCompatActivity() {

    private lateinit var activityDetailMovieBinding : ActivityDetailBinding
    private lateinit var detailContentBinding: ContentDetailMovieBinding
    private lateinit var detailTvShowViewModel: DetailTvShowViewModel
    private var menu: Menu? = null

    companion object {
        const val extraMovie = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailMovieBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailContent

        setContentView(activityDetailMovieBinding.root)

        setSupportActionBar(activityDetailMovieBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailFactory = VMAppFactory.getInstance(this)
        detailTvShowViewModel = ViewModelProvider(this, detailFactory)[DetailTvShowViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {

            val tempId = extras.getString(extraMovie)
            if (tempId != null ) {

                detailTvShowViewModel.setSelectedDetail(tempId)
                detailTvShowViewModel.detailTvShow.observe(this, { detailEntity ->

                        if (detailEntity != null) {
                            activityDetailMovieBinding.detailContent.apply{
                                when (detailEntity.status) {
                                    IndicatorStatus.LOADING -> progressbarDetailContent.visibility = View.VISIBLE
                                    IndicatorStatus.SUCCESS ->
                                        if (detailEntity.data != null) {
                                            progressbarDetailContent.visibility = View.VISIBLE
                                            populateCard(detailEntity.data)
                                        }
                                    IndicatorStatus.ERROR -> {
                                        progressbarDetailContent.visibility = View.GONE
                                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    })
            }
        }
    }

    private fun populateCard(entity: TvShowEntity) {
        Glide.with(this)
            .load(entity.poster)
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error))
            .into(detailContentBinding.imagePoster)

        detailContentBinding.apply {
            progressbarDetailContent.visibility = View.GONE
            textYear.text = entity.releaseYear
            textDuration.text = entity.duration
            textTitle.text = entity.title
            textGenre.text = entity.genre

            textQuote.text = entity.quote
            textOverview.text = entity.overview
            textStatus.text = entity.status
            textLang.text = entity.originalLanguage
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        detailTvShowViewModel.detailTvShow.observe(this, { detailTvShow ->
            if (detailTvShow != null) {
                detailContentBinding.apply{
                    when (detailTvShow.status) {
                        IndicatorStatus.LOADING -> progressbarDetailContent.visibility = View.VISIBLE
                        IndicatorStatus.SUCCESS -> if (detailTvShow.data != null) {
                            progressbarDetailContent.visibility = View.GONE
                            val state = detailTvShow.data.favorited
                            setBookmarkState(state)
                        }
                        IndicatorStatus.ERROR -> {
                            progressbarDetailContent.visibility = View.GONE
                            Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            detailTvShowViewModel.setFavoriteTvShow()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_full_favorite_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_24)
        }
    }
}