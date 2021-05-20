package com.dicoding.auliarosyida.moviesapp.ui.landingpage

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.auliarosyida.moviesapp.R
import com.dicoding.auliarosyida.moviesapp.ui.favmovietab.FavMovieFragment
import com.dicoding.auliarosyida.moviesapp.ui.favtvshowtab.FavTvShowFragment
import com.dicoding.auliarosyida.moviesapp.ui.movietab.MovieFragment
import com.dicoding.auliarosyida.moviesapp.ui.tvshowtab.TvShowFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.movies, R.string.tvshow, R.string.fav_movies, R.string.fav_tvshows)
    }

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> MovieFragment()
                1 -> TvShowFragment()
                2 -> FavMovieFragment()
                3 -> FavTvShowFragment()
                else -> Fragment()
            }

    override fun getPageTitle(position: Int): CharSequence = mContext.resources.getString(TAB_TITLES[position])

    override fun getCount(): Int = TAB_TITLES.size

}