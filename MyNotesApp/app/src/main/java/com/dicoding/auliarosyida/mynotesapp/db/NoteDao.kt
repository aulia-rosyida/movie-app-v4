package com.dicoding.auliarosyida.mynotesapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Kelas ini nantinya digunakan untuk melakukan eksekusi quiring.
 * */
@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * from note ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Note>>
}