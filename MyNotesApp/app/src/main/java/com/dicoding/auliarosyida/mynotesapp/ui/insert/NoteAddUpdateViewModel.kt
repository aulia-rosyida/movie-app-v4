package com.dicoding.auliarosyida.mynotesapp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.dicoding.auliarosyida.mynotesapp.db.Note
import com.dicoding.auliarosyida.mynotesapp.repo.NoteRepository

/**
 * Kelas ViewModel sebagai penghubung antara Activity dengan Repository
 * */
class NoteAddUpdateViewModel(application: Application) : ViewModel() {

    private val mNoteRepository: NoteRepository = NoteRepository(application)

    fun insert(note: Note) {
        mNoteRepository.insert(note)
    }

    fun update(note: Note) {
        mNoteRepository.update(note)
    }

    fun delete(note: Note) {
        mNoteRepository.delete(note)
    }
}