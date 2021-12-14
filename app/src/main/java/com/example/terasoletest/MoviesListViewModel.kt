package com.example.terasoletest

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MoviesListViewModel(application: Application):ViewModel() {

    // private mutable LiveData of List<DatabaseMovies> for this class only
    private val _moviesList = MutableLiveData<List<DatabaseMovie>>()

    // public moviesList to observe by LifeCycleOwner(MoviesListViewFragment.kt)
    val moviesList: LiveData<List<DatabaseMovie>> = _moviesList

    // database  repository for all the requests to database and network
    private var repository: Repository =
        Repository(getDatabase(application))

    // selectedGenresItemIndex, selectedSortItemIndex, selectedRatingIndex
    // stored here to survive state changes/rotation
    // and not to lost any data
    var selectedGenresItemIndex = 0
    var selectedSortItemIndex = 0
    var selectedRatingIndex = 0

    init {
        // load data from database
        loadMoviesFromDatabase()
    }

    // load data from database using coroutines
    // not blocking main thread
    private fun loadMoviesFromDatabase() {
        // launching coroutine in viewModelScope
        viewModelScope.launch {
            // updating the value local moviesList from repository
            _moviesList.value = repository.loadMoviesFromDatabase() as List<DatabaseMovie>?

            // if moviesList is null or empty
            // means we don't have data stored in our database
            // so load the data from Network
            if (_moviesList.value.isNullOrEmpty()) {
                loadMoviesFromNetwork()
            }
        }
    }
    // load data from Network using coroutines
    private fun loadMoviesFromNetwork() {
        // launching coroutine in viewModelScope
        viewModelScope.launch {
            // loading data from Network and setting listener
            repository.loadMoviesFromNetwork {
                // saved the data to database now we can fetch data from database
                loadMoviesFromDatabase()
            }
        }
    }

    // filterFunction for genres
    fun filter(genres: String) {
        viewModelScope.launch {
            // getting filtered data from database
            val data = repository.loadMoviesBasedOnGenres(genres)
            // if data is not null or empty then update our moviesList and display it
            // recyclerView
            if (!(data.isNullOrEmpty())) {
                _moviesList.value = data
            }
        }
    }

    // sort function to sort movies
    fun sort(sortBy: String) {
        viewModelScope.launch {
            // loading movies data according to sortBy
            val data = if (sortBy == "Title") {
                repository.sortMoviesByTitle()
            } else {
                repository.sortMoviesByYearDsc()
            }
            // if data is not null or empty then update our moviesList and display it
            // recyclerView
            if (!(data.isNullOrEmpty())) {
                _moviesList.value = data
            }
        }
    }

    // filter movies list by director name
    fun filterByDirectorName(name: String) {
        viewModelScope.launch {
            // loading data from repository
            val data = repository.loadMoviesBasedOnDirectors(name)
            // if data is not null or empty then update our moviesList and display it
            // recyclerView
            if (!(data.isNullOrEmpty())) {
                _moviesList.value = data
            }
        }
    }

    // filter movies by ratings
    // load movies with more than given param rating
    fun filterByRatings(rating: String) {
        viewModelScope.launch {
            // loading data from repository
            val data = repository.loadMoviesBasedOnRatings(rating)
            // if data is not null or empty then update our moviesList and display it
            // recyclerView
            if (!(data.isNullOrEmpty())) {
                _moviesList.value = data
            }
        }
    }


    // Factory class to create and return MoviesListViewModel object
    class Factory(val app:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MoviesListViewModel::class.java)){
            return MoviesListViewModel(app) as T
        }
        // modelClass is not MoviesListViewModel
        throw IllegalArgumentException("Unknown ViewModel")
    }

}}