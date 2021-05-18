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
    fun getAllNotes(): LiveData<PagedList<Note>> = LivePagedListBuilder(mNoteRepository.getAllNotes(), 20).build()
}