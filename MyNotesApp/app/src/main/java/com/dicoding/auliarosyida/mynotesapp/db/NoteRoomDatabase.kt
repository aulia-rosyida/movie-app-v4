package com.dicoding.auliarosyida.mynotesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
                        .build()
                }
            }
            return INSTANCE as NoteRoomDatabase
        }
    }

}