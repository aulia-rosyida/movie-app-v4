package com.dicoding.auliarosyida.moviesapp.ui.favtvshowtab

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.databinding.ItemsMovieBinding
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.ui.detailpage.DetailTvShowActivity

class FavTvShowAdapter: RecyclerView.Adapter<FavTvShowAdapter.CourseViewHolder>() {
    private val listFavShows = ArrayList<TvShowEntity>()

    fun setFavTvShows(favShows: List<TvShowEntity>?) {
        if (favShows == null) return
        this.listFavShows.clear()
        this.listFavShows.addAll(favShows)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemsFavMoviesBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsFavMoviesBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = listFavShows[position]
        holder.bind(course)
    }

    override fun getItemCount(): Int = listFavShows.size

    inner class CourseViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: TvShowEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(show.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)

                tvItemTitle.text = show.title
                tvItemDuration.text = show.duration
                tvItemQuotes.text = show.quote

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailTvShowActivity.extraMovie, show.tvShowId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}