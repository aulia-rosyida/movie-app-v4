package com.dicoding.auliarosyida.moviesapp.core.ui.movietab

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.auliarosyida.moviesapp.BuildConfig
import com.dicoding.auliarosyida.moviesapp.databinding.ItemsMovieBinding
import com.dicoding.auliarosyida.moviesapp.core.domain.model.Movie
import com.dicoding.auliarosyida.moviesapp.detailpage.DetailMovieActivity
import com.dicoding.auliarosyida.moviesapp.core.utils.ConstHelper
import com.dicoding.auliarosyida.moviesapp.core.utils.loadFromUrl

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var listData = ArrayList<Movie>()

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsAcademyBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listData[position]
        holder.bind(movie)
    }

    class MovieViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            with(binding) {

                movie.poster?.let {
                    imgPoster.loadFromUrl(BuildConfig.TMDB_URL_IMAGE + ConstHelper.SIZE_POSTER + it)
                }
                tvItemTitle.text = movie.title
                tvItemReleaseYear.text = movie.releaseYear?.subSequence(0,4)
                tvItemQuotes.text = movie.quote

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailMovieActivity::class.java)
                    intent.putExtra(ConstHelper.EXTRA_MOVIE, movie)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int= listData.size
}