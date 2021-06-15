package com.dicoding.auliarosyida.moviesapp.model.source.remotesource.network

import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.ListMovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("3/discover/movie?sort_by=popularity.desc&api_key=8dafff561f198363ddcd3fadbbe35349")
    fun getList(): Call<ListMovieResponse>
}