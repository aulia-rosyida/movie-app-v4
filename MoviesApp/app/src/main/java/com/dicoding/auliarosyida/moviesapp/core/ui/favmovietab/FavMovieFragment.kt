package com.dicoding.auliarosyida.moviesapp.core.ui.favmovietab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.auliarosyida.moviesapp.databinding.FragmentFavMovieBinding
import com.dicoding.auliarosyida.moviesapp.viewmodel.VMAppFactory

class FavMovieFragment : Fragment() {

    private lateinit var _fragmentFavoriteBinding: FragmentFavMovieBinding
    private val binding get() = _fragmentFavoriteBinding

    private lateinit var viewModel: FavMovieViewModel
    private lateinit var adapter: FavMovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _fragmentFavoriteBinding = FragmentFavMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = VMAppFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavMovieViewModel::class.java]

            adapter = FavMovieAdapter()
            binding.progressbarFavmovie.visibility = View.VISIBLE
            viewModel.getFavoriteMovies().observe(this, { movies ->
                binding.progressbarFavmovie.visibility = View.GONE
                adapter.setData(movies)
            })

            with(binding){
                rvFavMovie.layoutManager = LinearLayoutManager(context)
                rvFavMovie.setHasFixedSize(true)
                rvFavMovie.adapter = adapter
            }
        }
    }
}