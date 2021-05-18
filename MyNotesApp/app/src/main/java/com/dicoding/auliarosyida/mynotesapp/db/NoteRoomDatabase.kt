package com.dicoding.auliarosyida.mynotesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors

/**
 * Kelas ini akan digunakan untuk menginisialisasi database dalam aplikasi.
 *
 * annotation @Database dan memberikan turunan kelas dari RoomDatabase
 * maka sebuah kelas abstract tersebut sudah dikatakan sebagai RoomDatabase
 * */
@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase(){

    abstract fun noteDao(): NoteDao

    companion object {

        @Volatile
        private var INSTANCE: NoteRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NoteRoomDatabase {
            if (INSTANCE == null) {
                synchronized(NoteRoomDatabase::class.java) {
                    /**
                     * untuk membuat atau membangun database pada aplikasi dengan nama --> note_database.
                     *
                     * Dengan begitu, Anda bisa memanfaatkannya untuk digunakan di kelas lain,
                     * pada project ini Anda memakainya di kelas NoteRepository
                     * */
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NoteRoomDatabase::class.java, "note_database")
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                add()
                            }
                        })
                        .build()
                }
            }
            return INSTANCE as NoteRoomDatabase
        }

        private fun add() {
            Executors.newSingleThreadExecutor().execute {
                val list: MutableList<Note> = ArrayList()
                for (i in 0..9) {
                    val dummyNote = Note()
                    dummyNote.title = "Tugas $i"
                    dummyNote.description = "Belajar Modul $i"
                    dummyNote.date= "2019/09/09 09:09:0$i"
                    list.add(dummyNote)
                }
                INSTANCE?.noteDao()?.insertAll(list)
            }
        }
    }

}