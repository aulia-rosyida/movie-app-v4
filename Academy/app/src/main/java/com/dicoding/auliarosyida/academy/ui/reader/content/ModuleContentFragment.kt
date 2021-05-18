package com.dicoding.auliarosyida.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.auliarosyida.academy.data.source.local.entity.ModuleEntity
import com.dicoding.auliarosyida.academy.databinding.FragmentModuleContentBinding
import com.dicoding.auliarosyida.academy.ui.reader.CourseReaderViewModel
import com.dicoding.auliarosyida.academy.viewmodel.ViewModelFactory
import com.dicoding.auliarosyida.academy.vo.Status

/**
 * ModuleContentFragment: Menampilkan Content dari Module yang dipilih
 * */
class ModuleContentFragment : Fragment() {

    companion object {
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance(): ModuleContentFragment = ModuleContentFragment()
    }

    private lateinit var fragmentModuleContentBinding: FragmentModuleContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentModuleContentBinding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return fragmentModuleContentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            //Fragment akan menggunakan ViewModel yang ada pada Activity (shared ViewModel).
            // Jika Anda ganti requireActivity() dengan this, maka Fragment tidak akan mengambil ViewModel dari Activity tetapi akan membuat ViewModel baru.
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]

            fragmentModuleContentBinding.progressBar.visibility = View.VISIBLE
            viewModel.selectedModule.observe(viewLifecycleOwner, { moduleEntity ->
                if (moduleEntity != null) {
                    when (moduleEntity.status) {
                        Status.LOADING -> fragmentModuleContentBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> if (moduleEntity.data != null) {
                            fragmentModuleContentBinding.progressBar.visibility = View.GONE
                            if (moduleEntity.data.contentEntity != null) {
                                populateWebView(moduleEntity.data)
                            }
//                            setButtonNextPrevState(moduleEntity.data)
                            if (!moduleEntity.data.read) {
                                viewModel.readContent(moduleEntity.data)
                            }
                        }
                        Status.ERROR -> {
                            fragmentModuleContentBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun populateWebView(module: ModuleEntity) {
        fragmentModuleContentBinding.webView.loadData(module.contentEntity?.content ?: "", "text/html", "UTF-8")
    }
}