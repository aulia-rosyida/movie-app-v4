package com.dicoding.auliarosyida.mynotesapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.auliarosyida.mynotesapp.ui.insert.NoteAddUpdateViewModel
import com.dicoding.auliarosyida.mynotesapp.ui.main.MainViewModel

/**
 * Mengapa kita perlu menggunakan ViewModelFactory?
 * Kelas ini berfungsi untuk menambahkan context ketika memanggil kelas ViewModel di dalam Activity
 * nantinya digunakan untuk inisialisasi database di dalam NoteRepository.
 *
 * Penggunaan factory ini juga bisa digunakan untuk mengirim parameter lainnya.
 * */
class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    /**
     * Dengan cara ini, kita bisa mengirimkan parameter context dengan nama mApplication ke kelas MainViewModel.
     * Anda juga dapat menyesuaikan parameter apa saja yang dimasukkan pada masing-masing ViewModel.
     * */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}