package com.dicoding.auliarosyida.academy.ui.academy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.auliarosyida.academy.databinding.FragmentAcademyBinding
import com.dicoding.auliarosyida.academy.viewmodel.ViewModelFactory
import com.dicoding.auliarosyida.academy.vo.Status

/**
 * AcademyFragment: Digunakan untuk menampilkan semua Course.
 * */
class AcademyFragment : Fragment() {

    private lateinit var fragmentAcademyBinding: FragmentAcademyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentAcademyBinding = FragmentAcademyBinding.inflate(layoutInflater, container, false)
        return fragmentAcademyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            //Ketika Activity atau Fragment membutuhkan ViewModel, maka akan memanggil kelas Factory untuk membuat ViewModel.
            val factory = ViewModelFactory.getInstance(requireActivity()) // inisialisasi ViewModelFactory

            // ganti default factory dengan factory yang sudah Anda buat
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]

            val academyAdapter = AcademyAdapter()

            // kode untuk melakukan observe untuk membaca LiveData
            fragmentAcademyBinding.progressBar.visibility = View.VISIBLE
            viewModel.getCourses().observe(viewLifecycleOwner, { courses ->
                if (courses != null) {
                    when (courses.status) {
                        Status.LOADING -> fragmentAcademyBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentAcademyBinding.progressBar.visibility = View.GONE
                            academyAdapter.setCourses(courses.data)
                            academyAdapter.notifyDataSetChanged()
                        }
                        Status.ERROR -> {
                            fragmentAcademyBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentAcademyBinding.rvAcademy) {
                this.layoutManager = LinearLayoutManager(context)
                this.setHasFixedSize(true)
                this.adapter = academyAdapter
            }
        }
    }
}