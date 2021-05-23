package com.dicoding.auliarosyida.moviesapp.ui.tvshowtab

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
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.ui.detailpage.DetailMovieActivity
import com.dicoding.auliarosyida.moviesapp.ui.detailpage.DetailTvShowActivity

class TvShowAdapter : PagedListAdapter<TvShowEntity, TvShowAdapter.CourseViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.tvShowId == newItem.tvShowId
            }
            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val itemsAcademyBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(itemsAcademyBinding)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val tvshow = getItem(position)
        if (tvshow != null) {
            holder.bind(tvshow)
        }
    }

    class CourseViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: TvShowEntity) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(tvShow.poster)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(imgPoster)

                tvItemTitle.text = tvShow.title
                tvItemDuration.text = tvShow.duration
                tvItemQuotes.text = tvShow.quote

                itemView.setOnClickListener {
                    val intent = Intent(it.context, DetailTvShowActivity::class.java)
                    intent.putExtra(DetailMovieActivity.extraMovie, tvShow.tvShowId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}