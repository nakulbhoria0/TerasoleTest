package com.example.terasoletest

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDAO {

    // get all movies from movie table
    @Query("select * from movie")
    suspend fun getAllMovies(): List<DatabaseMovie>

    // insert all movies to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<DatabaseMovie>)

    // load movies base on genres
    // return List<DatabaseMovie>
    @Query("select * from movie where genres like :genres")
    suspend fun loadMoviesBasedOnGenres(genres: String): List<DatabaseMovie>

    // load movies base on Directors
    // return List<DatabaseMovie>
    @Query("select * from movie where directors like :director")
    suspend fun loadMoviesBasedOnDirectors(director: String): List<DatabaseMovie>

    // load movies base on Ratings
    // return List<DatabaseMovie>
    @Query("select * from movie where rating > :ratings")
    suspend fun loadMoviesBasedOnRatings(ratings: Double): List<DatabaseMovie>

    // load sorted movies based on Title Ascending order
    // return List<DatabaseMovie>
    @Query("select * from movie order by title asc")
    suspend fun sortMoviesByTitle(): List<DatabaseMovie>

    // load sorted movies based on Year Descending order/ newer first
    // return List<DatabaseMovie>
    @Query("select * from movie order by year desc")
    suspend fun sortMoviesByYearDsc(): List<DatabaseMovie>
}