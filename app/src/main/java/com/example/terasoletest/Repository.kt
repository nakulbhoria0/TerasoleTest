package com.example.terasoletest

class Repository(val database: MoviesDatabase) {

    // load movies from database and return List of DatabaseMovie
    suspend fun loadMoviesFromDatabase(): List<DatabaseMovie?> {
        return database.MoviesDao.getAllMovies()
    }

    // load movies from network and save loaded movies to database
    // and a callback function
    suspend fun loadMoviesFromNetwork(function: () -> Unit) {
        val data = createMovieService().getMovies()

        // transform Movie object to DatabaseMovie object
        // so it will be easy to work with them in database
        val databaseMovies = data.asDatabaseModel()

        //save loaded movies to database
        database.MoviesDao.insertAll(databaseMovies)

        // callback function after all data loaded from network and saved to database
        function()
    }

    // load movies based on genres from database and return List of DatabaseMovie
    suspend fun loadMoviesBasedOnGenres(genres: String):List<DatabaseMovie>{
        return database.MoviesDao.loadMoviesBasedOnGenres("%${genres}%")
    }

    // load movies based on Ratings from database and return List of DatabaseMovie
    suspend fun loadMoviesBasedOnRatings(rating:String): List<DatabaseMovie> {
        return database.MoviesDao.loadMoviesBasedOnRatings(rating.toDouble())
    }

    // load movies based on Director's name from database and return List of DatabaseMovie
    suspend fun loadMoviesBasedOnDirectors(name:String): List<DatabaseMovie> {
        return database.MoviesDao.loadMoviesBasedOnDirectors("%${name}%")
    }



    // sort movies based on Title Ascending order and return List of DatabaseMovie
    suspend fun sortMoviesByTitle(): List<DatabaseMovie> {
        return database.MoviesDao.sortMoviesByTitle()

    }

    // sort movies based on year Dsc = Descending order or Earlier first
    // return List of DatabaseMovie
    suspend fun sortMoviesByYearDsc(): List<DatabaseMovie> {
        return database.MoviesDao.sortMoviesByYearDsc()
    }



}