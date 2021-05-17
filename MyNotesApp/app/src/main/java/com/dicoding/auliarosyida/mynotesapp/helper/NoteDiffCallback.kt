package com.dicoding.auliarosyida.mynotesapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.auliarosyida.mynotesapp.db.Note

/**
 * NoteDiffCallback untuk melakukan pengecekan apakah ada perubahan list note.
 * Kelas ini nanti akan dipanggil di kelas adapter.
 * */
class NoteDiffCallback(private val mOldNoteList: List<Note>, private val mNewNoteList: List<Note>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return mOldNoteList.size
    }

    override fun getNewListSize(): Int {
        return mNewNoteList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]
        return oldEmployee.title == newEmployee.title && oldEmployee.description == newEmployee.description
    }

}