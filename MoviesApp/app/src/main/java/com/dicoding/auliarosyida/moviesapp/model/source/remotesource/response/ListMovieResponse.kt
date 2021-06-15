package com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response

import com.google.gson.annotations.SerializedName

data class ListMovieResponse(

    @field:SerializedName("results")
    val places: List<MovieResponse>
)