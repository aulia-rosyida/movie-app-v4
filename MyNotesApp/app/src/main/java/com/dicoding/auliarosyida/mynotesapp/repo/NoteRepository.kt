package com.dicoding.auliarosyida.mynotesapp.repo

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.auliarosyida.mynotesapp.db.Note
import com.dicoding.auliarosyida.mynotesapp.db.NoteDao
import com.dicoding.auliarosyida.mynotesapp.db.NoteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Kelas ini berfungsi sebagai penghubung antara ViewModel dengan database atau resource data.
 * */
class NoteRepository (application: Application) {

    private val mNotesDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * NoteDao bisa langsung Anda gunakan dengan cara mendefinisikan RoomDatabase.
     * Dao (Data Access Object) akan secara otomatis terhubung dengan RoomDatabase,
     * selama kelas tersebut sudah diberi annotation @Dao.
     * Jadi Anda bisa memakainya untuk menggunakanya ke kelas-kelas lain.
     * */
    init {
        val db = NoteRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    /** seperti untuk mendapatkan semua data Note*/
    fun getAllNotes(): LiveData<List<Note>> = mNotesDao.getAllNotes()

    /**
     * pada bagian insert, update dan delete, aksi tersebut harus dijalankan menggunakan ExecutorService.
     * Jika proses di atas hanya dilakukan tanpa executorService, maka akan terjadi force close.
     *
     * Hal ini disebabkan karena proses insert, update dan delete menggunakan thread yang berbeda
     * --> yakni background thread.
     * */
    fun insert(note: Note) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: Note) {
        executorService.execute { mNotesDao.delete(note) }
    }

    fun update(note: Note) {
        executorService.execute { mNotesDao.update(note) }
    }
}
