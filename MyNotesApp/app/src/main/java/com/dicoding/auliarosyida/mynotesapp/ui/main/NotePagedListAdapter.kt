package com.dicoding.auliarosyida.mynotesapp.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.auliarosyida.mynotesapp.databinding.ItemNoteBinding
import com.dicoding.auliarosyida.mynotesapp.db.Note
import com.dicoding.auliarosyida.mynotesapp.ui.insert.NoteAddUpdateActivity

/**
 * Penggunaan PagedListAdapter hampir mirip dengan RecyclerView.Adapter.
 * Sama-sama menghasilkan metode onCreateViewHolder dan onBindViewHolder, isi dari metodenya pun juga sama.
 *
 * Bedanya yaitu untuk mengambil data pada PagedListAdapter menggunakan getItem(position)
 *
 * Selain itu ia mampu membedakan perubahan antara data baru dan lama.
 * Sebabnya, PagedListAdapter sudah mendukung DiffUtil.ItemCallback
 * untuk merubah tampilan sesuai ketika terjadi perubahan data.
 * */
class NotePagedListAdapter (private val activity: Activity) : PagedListAdapter<Note, NotePagedListAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position) as Note)
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(activity, NoteAddUpdateActivity::class.java)
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note)
                    activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE)
                }
            }
        }
    }

    companion object {

        /**
         *  jika data judul dan deskrispi pada note tersebut masih sama, maka RecyclerView tidak di-update.
         *  Jika berbeda, maka list akan di-update.
         * */
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldNote: Note, newNote: Note): Boolean {
                return oldNote.title == newNote.title && oldNote.description == newNote.description
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldNote: Note, newNote: Note): Boolean {
                return oldNote == newNote
            }
        }
    }
}