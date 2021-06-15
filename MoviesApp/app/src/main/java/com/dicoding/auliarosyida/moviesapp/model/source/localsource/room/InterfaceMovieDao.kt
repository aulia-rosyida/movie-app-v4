package com.dicoding.auliarosyida.moviesapp.model.source.localsource.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import androidx.paging.DataSource

@Dao
interface InterfaceMovieDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentities where favorited = 1")
    fun getFavoritedMovies(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

}