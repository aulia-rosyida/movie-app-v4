package com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieentities")
data class MovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: String,

    @ColumnInfo(name = "poster")
    var poster: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "quote")
    var quote: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "releaseYear")
    var releaseYear: String,

    @ColumnInfo(name = "genre")
    var genre: String,

    @ColumnInfo(name = "duration")
    var duration: String,

    @ColumnInfo(name = "status")
    var status: String,

    @ColumnInfo(name = "originalLanguage")
    var originalLanguage: String,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false
)
