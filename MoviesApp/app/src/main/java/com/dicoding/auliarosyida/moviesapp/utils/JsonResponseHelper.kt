package com.dicoding.auliarosyida.moviesapp.utils

import android.content.Context
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonResponseHelper(private val context: Context) {

    //ubah file JSON menjadi String
    private fun parsingFileJsonToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<MovieResponse> {
        val listMovies = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileJsonToString("MovieResponses.json").toString())
            val listArrayMovie = responseObject.getJSONArray("movies")
            val gson = Gson()
            for (i in 0 until listArrayMovie.length()) {
                val movieObject = listArrayMovie.getJSONObject(i)
                val dataMovie = gson.fromJson(movieObject.toString(), MovieResponse::class.java)
                listMovies.add(dataMovie)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return listMovies
    }

    fun loadMovie(movieId: String): MovieResponse {
        val listMovies = loadMovies()
        lateinit var aMovie: MovieResponse
        for (response in listMovies) {
            if (response.id == movieId) {
                aMovie = response
            }
        }
        return aMovie
    }
}