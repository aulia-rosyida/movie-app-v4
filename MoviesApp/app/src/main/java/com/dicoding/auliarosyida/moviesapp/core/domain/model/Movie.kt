package com.dicoding.auliarosyida.moviesapp.core.domain.model

import android.os.Parcelable
import com.dicoding.auliarosyida.moviesapp.core.data.source.remotesource.response.Genre
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    var id: String = "",
    var poster: String = "",
    var title: String = "",
    var quote: String = "",
    var overview: String = "",
    var releaseYear: String = "",
    var genreIds: ArrayList<Genre>,
    var duration: String = "0m",
    var status: String = "",
    var originalLanguage: String = ""
) : Parcelable