package com.dicoding.auliarosyida.mynotesapp.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

/**
 * Kelas ini nantinya digunakan untuk melakukan eksekusi quiring.
 *  --> aksi CRUD(Create, Read, Update dan Delete).
 *
 *  Sebuah kelas interface yang diberi sebuah annotation @dao akan menjadi sebuah kelas Dao secara otomatis.
 * */
@Dao
interface NoteDao { //DAO = Data Access Object

    /**
     * @Insert untuk query insert pada database sesuai dengan input entitas yang dimasukkan,
     * contohnya jika pada perintah di bawah adalah Note
     * */
    @Insert(onConflict = OnConflictStrategy.IGNORE) //OnConflictStrategy.IGNORE digunakan
                                                    // jika data yang dimasukkan datanya sama maka dibiarkan saja.
    fun insert(note: Note)

    //Untuk menambahkan data dummy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<Note>)

    /**
     * @Update untuk query update item pada database, contohnya adalah mengupdate sebuah item Note.
     * */
    @Update
    fun update(note: Note)

    /**
     * @Delete untuk menghapus sebuah item tertentu,
     * contohnya pada baris tersebut akan menghapus sebuah item note dengan cara mencocokan item mana yang sama.
     * */
    @Delete
    fun delete(note: Note)

    /**
     * Anotasi @RawQuery digunakan untuk menandai bahwa fungsi tersebut menggunakan fitur RawQuery,
     * akibatnya Anda harus menambahkan parameter berupa SupportSQLiteQuery.
     *
     * Selain itu supaya data tersebut bisa di-observe ketika ada perubahan data,
     * gunakanlah observedEntities = Note.class
     * */
    @RawQuery(observedEntities = [Note::class])
    fun getAllNotes(query: SupportSQLiteQuery): DataSource.Factory<Int, Note>

}