package com.dicoding.auliarosyida.moviesapp.ui.favmovietab

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.databinding.ItemsMovieBinding
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.ui.detailpage.DetailMovieActivity

class FavMovieAdapter: RecyclerView.Adapter<FavMovieAdapter.CourseViewHolder>() {
    private val listFavMovies = ArrayList<MovieEntity>()

    fun setFavMovies(favMovies: List<MovieEntity>?) {
        if (favMovies == null) {
            return
        }
        this.listFavMovies.clear()
        this.listFavMovies.addAll(favMovies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemsFavMoviesBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsFavMoviesBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val fav = listFavMovies[position]
        holder.bind(fav)
    }

    override fun getItemCount(): Int = listFavMovies.size

    inner class CourseViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root) {
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