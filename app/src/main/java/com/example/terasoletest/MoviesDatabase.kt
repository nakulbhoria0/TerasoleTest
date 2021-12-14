package com.example.terasoletest

import android.content.Context
import androidx.room.*





@Database(entities = [DatabaseMovie::class],version = 1 , exportSchema = false)
abstract class MoviesDatabase:RoomDatabase() {
    // dao object to access the function of MoviesDAO class
    abstract val MoviesDao:MoviesDAO
}

// only one instance of MoviesDatabase
private lateinit var INSTANCE:MoviesDatabase

// build and return database instance
fun getDatabase(context:Context):MoviesDatabase{
    synchronized(MoviesDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context,
                MoviesDatabase::class.java,
                "movies_database"
            ).build()
        }
    }
    return INSTANCE
}