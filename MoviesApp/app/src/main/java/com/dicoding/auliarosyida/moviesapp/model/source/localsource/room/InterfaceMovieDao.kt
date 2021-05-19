package com.dicoding.auliarosyida.moviesapp.model.source.localsource.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.MovieEntity
import com.dicoding.auliarosyida.moviesapp.model.source.localsource.entity.TvShowEntity
import com.dicoding.auliarosyida.moviesapp.model.source.remotesource.response.MovieResponse

@Dao
interface InterfaceMovieDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movieentities where favorited = 1")
    fun getFavoritedMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(courses: List<MovieEntity>)

    @Update
    fun updateMovie(course: MovieEntity)

    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<MovieEntity>

    @Query("SELECT * FROM tvshowentities")
    fun getTvShows(): LiveData<List<TvShowEntity>>

    @Query("SELECT * FROM tvshowentities where favorited = 1")
    fun getFavoritedTvShows(): LiveData<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(courses: List<TvShowEntity>)

    @Update
    fun updateTvShow(course: TvShowEntity)

    @Query("SELECT * FROM tvshowentities WHERE tvShowId = :tvShowId")
    fun getTvShowById(tvShowId: String): LiveData<TvShowEntity>

}