Libraries used

Material Design(Designing Library from Google)
Retrofit(Networking Library from Squareup)
Moshi(Convert Library for Retrofit)
Room Database(Androidx Library)
Navigation, safeargs(Androidx Library)
ViewModel, LiveData, ViewBinding(Androidx Lifecycle Library)
Coil(Image Loading Library)


Classes 
DatabaseMovieModel - class holding Model data of DatabaseMovie which a table of our database
Repository - Repository is a class where we are getting data from database and API. Fragment always get the data from repository instead dao and API service.
Movie - Model class for data we are getting from API
MoviesDetailsFragment - to show this fragment click on any list item in list from MoviesListViewFragment, this fragment needs an argument type of DatabaseMovie to display all the details of the Movie.
MoviesDetailsViewModel - ViewModel for MoviesDetailsFragment and takes care of arguments
MoviesDAO - Data Access Object of Database. Request queries to database.
MoviesDatabase - Room Database creates and return Instance of Database and holding instance of MoviesDAO interface
MovieService - Creates API Service and call Functions to API
MoviesListViewModel - Home page for Application where we display list of all movies. with 2 fab buttons one os for sorting and other one is for filter. selecting any of them reload the data according to selected preferences. click on item will lead go to MovieDetailsFragment(required parameter DatabaseMovie) where all the Details of the Movie is displayed.