package com.dicoding.auliarosyida.moviesapp.model.source.localsource.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity

@Database(entities = [MovieEntity::class],
    version = 1,
    exportSchema = false)
abstract class MovieBuilderDatabase : RoomDatabase(){

    abstract fun movieDao(): InterfaceMovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieBuilderDatabase? = null

        fun getInstance(context: Context): MovieBuilderDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieBuilderDatabase::class.java,
                    "Movies.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}