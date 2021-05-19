package com.dicoding.auliarosyida.moviesapp.ui.detailpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.databinding.ActivityDetailBinding
import com.dicoding.auliarosyida.moviesapp.databinding.ContentDetailMovieBinding
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.dicoding.auliarosyida.moviesapp.viewmodel.VMAppFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var detailContentBinding: ContentDetailMovieBinding

    companion object {
        const val extraMovie = "extra_movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityDetailMovieBinding = ActivityDetailBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailMovieBinding.detailContent

        setContentView(activityDetailMovieBinding.root)

        setSupportActionBar(activityDetailMovieBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailFactory = VMAppFactory.getInstance(this)
        val detailViewModel = ViewModelProvider(this, detailFactory)[DetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {

            val tempId = extras.getString(extraMovie)
            if (tempId != null ) {

                detailViewModel.setSelectedDetail(tempId)
                detailViewModel.getEntity().observe(this, { detailEntity -> populateCard(detailEntity) })

            }
        }
    }

    private fun populateCard(entity: MovieResponse) {
        Glide.with(this)
            .load(entity.poster)
            .transform(RoundedCorners(20))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
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
}