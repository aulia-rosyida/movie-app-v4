package com.dicoding.auliarosyida.mynotesapp.helper

import androidx.sqlite.db.SimpleSQLiteQuery

/** variabel dan method untuk membuat query */
object SortUtils {

    /***
     *  digunakan untuk menentukan tipe urutan.
     *
     *  NEWEST digunakan untuk mengurutkan data berdasarkan yang terbaru,
     *  OLDEST digunakan untuk mengurutkan data berdasarkan yang terlama.
     *  RANDOM untuk mengurutkan secara random
     */
    const val NEWEST = "Newest"
    const val OLDEST = "Oldest"
    const val RANDOM = "Random"

    /** untuk membuat query, Anda menggunakan kode berikut:
     *
     * Method ini mengembalikan nilai SimpleSQLiteQuery yang dibutuhkan RawQuery*/
    fun getSortedQuery(filter: String): SimpleSQLiteQuery {

        val simpleQuery = StringBuilder().append("SELECT * FROM note ")

        if (filter == NEWEST) {
            simpleQuery.append("ORDER BY id DESC")

        } else if (filter == OLDEST) {
            simpleQuery.append("ORDER BY id ASC")

        } else if (filter == RANDOM) {
            simpleQuery.append("ORDER BY RANDOM()")
        }

        return SimpleSQLiteQuery(simpleQuery.toString())

        //Untuk penggunaannya, method ini dipakai di kelas NoteRepository
    }
}