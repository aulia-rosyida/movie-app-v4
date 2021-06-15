package com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.auliarosyida.moviesapp.core.data.source.localsource.entity.MovieEntity
import androidx.paging.DataSource

@Dao
interface InterfaceMovieDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movieentities where favorited = 1")
    fun getFavoritedMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movieentities WHERE id = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

}