package com.example.terasoletest

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoviesDetailsViewModel(val app: Application): ViewModel() {

    // MovieDetailsFragmentArgs to store arguments of fragment
    lateinit var args:MovieDetailsFragmentArgs

    // set the args to arguments received by MovieDetailFragment
    fun setArguments(arguments: MovieDetailsFragmentArgs) {
        // assigning args to received arguments
        args = arguments
    }
    // Factory class to create and return MoviesDetailsViewModel object
    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MoviesDetailsViewModel::class.java)){
                return MoviesDetailsViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel")
        }
}
}