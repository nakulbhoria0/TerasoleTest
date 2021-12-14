package com.example.terasoletest

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// building retrofit service for MovieService class and returning it
fun createMovieService():MovieService{
    val retrofit = Retrofit.Builder()
            // base url for api
        .baseUrl("http://test.terasol.in/")
            // converterFactory to convert response into our Model Class
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    // returning retrofit object
    return retrofit.create(MovieService::class.java)
}

interface MovieService{
    // suspended getMovies function to get all the Movies Data and return list of Movie
    @GET("moviedata.json")
    suspend fun getMovies():List<Movie>
}