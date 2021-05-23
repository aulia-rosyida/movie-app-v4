package com.dicoding.auliarosyida.moviesapp.ui.movietab

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.databinding.ItemsMovieBinding
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.ui.detailpage.DetailMovieActivity

class MovieAdapter: PagedListAdapter<MovieEntity, MovieAdapter.CourseViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }
            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemsAcademyBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    class CourseViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(movie.poster)
                        .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                        .into(imgPoster)

                tvItemTitle.text = movie.title
                tvItemDuration.text = movie.duration
                tvItemQuotes.text = movie.quote

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.extraMovie, movie.movieId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}