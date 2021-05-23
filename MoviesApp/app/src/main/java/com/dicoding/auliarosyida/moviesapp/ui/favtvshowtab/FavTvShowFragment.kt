package com.dicoding.auliarosyida.moviesapp.ui.favtvshowtab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.auliarosyida.moviesapp.databinding.FragmentFavTvshowBinding
import com.dicoding.auliarosyida.moviesapp.viewmodel.VMAppFactory

class FavTvShowFragment : Fragment() {

    private var _fragmentFavoriteBinding: FragmentFavTvshowBinding? = null
    private val binding get() = _fragmentFavoriteBinding

    private lateinit var viewModel: FavTvShowViewModel
    private lateinit var adapter: FavTvShowAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _fragmentFavoriteBinding = FragmentFavTvshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = VMAppFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[FavTvShowViewModel::class.java]

            adapter = FavTvShowAdapter()
            binding?.progressbarFavtvshow?.visibility = View.VISIBLE
            viewModel.getFavoriteTvShows().observe(this, { shows ->
                binding?.progressbarFavtvshow?.visibility = View.GONE
                adapter.submitList(shows)
            })

            binding?.rvFavTvshow?.layoutManager = LinearLayoutManager(context)
            binding?.rvFavTvshow?.setHasFixedSize(true)
            binding?.rvFavTvshow?.adapter = adapter
        }
    }
}