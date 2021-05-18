package com.dicoding.auliarosyida.mynotesapp.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.auliarosyida.mynotesapp.db.Note
import com.dicoding.auliarosyida.mynotesapp.repo.NoteRepository

/**
 * kelas ViewModel jadi lebih singkat,
 * hanya menginisialisasi kelas Repository dan mengambil fungsi yang ada pada kelas tersebut
 * */
class MainViewModel(application: Application) : ViewModel() {

    private val mNoteRepository: NoteRepository = NoteRepository(application)

    // memanggil getAllNotes(), Activity dengan mudah meng-observe data list notes dan bisa segera ditampilkan.

    /**
     * LivePagedListBuilder digunakan untuk merubah DataSource menjadi PagedList dalam bentuk LiveData.
     * PagedList adalah daftar yang memuat data dalam potongan (halaman) dari DataSource.
     * Item dapat diakses dengan get (int), dan pemuatan lebih lanjut yang dapat dipicu dengan loadAround (int).
     * Jika diperhatikan, nilai 20 tersebut berarti list akan dimuat setiap 20 item.
     * */
    fun getAllNotes(): LiveData<PagedList<Note>> = LivePagedListBuilder(mNoteRepository.getAllNotes(), 20).build()
}