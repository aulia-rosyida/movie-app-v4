package com.dicoding.auliarosyida.mynotesapp.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  awalnya adalah sebuah data model Note.
 *  Namun bisa dengan mudah menjadi sebuah table dalam Room dengan menambahkan annotation @Entity.
 *
 *  Default dari nama tabel adalah sesuai dengan nama kelas tersebut.
 *  Kemudian variabel yang di dalamnya akan menjadi column dari tabel Note.
 * */
@Entity
@Parcelize
data class Note (

        /**
         * @PrimaryKey digunakan untuk menjadikan sebuah column menjadi primary key.
         * Sedangkan autoGenerate digunakan untuk membuat id secara otomatis.
         *
         * Prinsipnya sama seperti ketika Anda menggunakan database SQL.
         * */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") //Kode @ColumnInfo digunakan untuk memberi nama column dari tabel.
                            // Jika tidak diberi nama, maka default dari nama column adalah variable tersebut.
    var id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable
