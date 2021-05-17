package com.dicoding.auliarosyida.mynotesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Kelas ini akan digunakan untuk menginisialisasi database dalam aplikasi.
 * */
@Database(entities = [Note::class], version = 1)
abstract class NoteRoomDatabase : RoomDatabase(){

}