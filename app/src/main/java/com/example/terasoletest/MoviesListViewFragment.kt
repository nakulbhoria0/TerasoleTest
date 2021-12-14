package com.example.terasoletest

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.terasoletest.databinding.FragmentMoviesListViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MoviesListViewFragment : Fragment() {
    // binding object
    private lateinit var binding: FragmentMoviesListViewBinding

    // adapter for recyclerView
    private lateinit var moviesAdapter: MoviesAdapter

    // recyclerView to display our moviesList
    private lateinit var moviesRecyclerView: RecyclerView

    // initializing viewModel
    private val viewModel:MoviesListViewModel by lazy {
        val activity = requireNotNull(this.activity){}
        ViewModelProvider(this,MoviesListViewModel.Factory(activity.application))
            .get(MoviesListViewModel::class.java)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // initializing binding
        binding = FragmentMoviesListViewBinding.inflate(inflater, container, false)

        // initializing recyclerView
        moviesRecyclerView = binding.moviesRecyclerView

        // GridLayoutManager to display list in grids
        moviesRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)

        // observing viewModel.moviesList
        viewModel.moviesList.observe(viewLifecycleOwner,
            { list ->
                // setting adapter when list is not null or empty
                if(!list.isNullOrEmpty()){
                    setAdapter(list)
                }
            })

        // filterButton clickListener
        binding.filterButton.setOnClickListener {
            // show dialog to choose filter option
            showFilterDialog()
        }
        // sortButton clicklistener
        binding.sortButton.setOnClickListener {
            // show dialog to choose sort option
            showSortDialog()
        }



        return binding.root

    }

    /**
     * function to set the adapter
     * @param list is our Movies List
     */
    private fun setAdapter(list: List<DatabaseMovie>) {

        // initializing adapter and setting clickListener to listItems
        moviesAdapter = MoviesAdapter(list){databaseMovie ->
            // sending DatabaseMovie object to MoviesDetailsFragment to show details
            // using safeArgs plugin
            val movieDetailsFragment = MoviesListViewFragmentDirections
                .actionMoviesListViewFragmentToMovieDetailsFragment(databaseMovie)

            findNavController().navigate(movieDetailsFragment)
        }
        // setting adapter to recyclerView
        moviesRecyclerView.adapter = moviesAdapter
    }


    // show dialog to choose Genres to filter Movies
    private fun showGenresDialog(){

        // list of Genres
        val list = arrayOf("Action", "Biography", "Drama", "Sport", "Crime", "Thriller", "Adventure", "Fantasy", "Comedy", "Horror", "Mystery")

        // current selected Genres
        var selectedGenres = list[viewModel.selectedGenresItemIndex]

        // DialogBuilder
        MaterialAlertDialogBuilder(requireContext())
                // title of the dialog
            .setTitle("Genres")
                // setSingleChoice because we want only 1 genre
            .setSingleChoiceItems(list, viewModel.selectedGenresItemIndex){ dialog, which->
                // updating selectedGenresItemIndex in ViewModel
                viewModel.selectedGenresItemIndex = which
                //updating selectedGenres
                selectedGenres = list.get(viewModel.selectedGenresItemIndex)
            }
                // setting button and clickListener
            .setPositiveButton("OK"){dialog, which ->
                // filter list based on selected genre
                viewModel.filter(selectedGenres)
            }
                // setting cancel button
            .setNeutralButton("cancel"){dialog, whick->
                // hide dialog
            }
                // displaying the dialog
            .show()

    }
    fun showSortDialog(){

        // list of sortBy
        val list = arrayOf("Title","Year")

        // selectedSortType by default at index 0
        var selectedSortType = list[viewModel.selectedSortItemIndex]

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sort by")
            // setSingleChoice because we want only 1 sort type
            .setSingleChoiceItems(list, viewModel.selectedSortItemIndex){ dialog, which->
                // updating selectedSortItemIndex
                viewModel.selectedSortItemIndex = which
                // updating selectedSortType
                selectedSortType = list.get(viewModel.selectedSortItemIndex)
            }
            // setting button and clickListener
            .setPositiveButton("OK"){dialog, which ->

                // sort list based on selected sort type
                viewModel.sort(selectedSortType)
            }
            // setting cancel button
            .setNeutralButton("cancel"){dialog, whick->
                // hide dialog
            }
            .show()

    }

    fun showFilterDialog(){

        // list of filter options
            val list = arrayOf("Genres","Ratings","Directors")


            MaterialAlertDialogBuilder(requireContext())
                    // set title
                .setTitle("Filter")
                    //setting items and clickListener show appropriate dialog
                .setItems(list){ dialog, which->
                    if(which == 0){ // which == Genres
                    showGenresDialog()
                }else if(which == 1){ // which == Ratings
                    showRatingsDialog()
                }else{ // which == Directors
                    showFilterDirectorDialog()
                }
                }
                    //show dialog
                .show()
    }

    private fun showRatingsDialog() {
        // list of ratings
        val list = arrayOf("5.0+","6.0+","7.0+","8.0+","9.0+")

        MaterialAlertDialogBuilder(requireContext())
                // title
            .setTitle("Select Rating")
            // setSingleChoice because we want only one rating to be selected
            .setSingleChoiceItems(list, viewModel.selectedRatingIndex){ dialog, which->
                // updating selectedRatingIndex in viewModel
                viewModel.selectedRatingIndex = which
            }
                // set positive button and clickListener
            .setPositiveButton("OK"){dialog, which ->
                // using when statement to find and send comparable rating
                // which rating is selected
                when(viewModel.selectedRatingIndex){
                    0 -> viewModel.filterByRatings("5.0")
                    1 -> viewModel.filterByRatings("6.0")
                    2 -> viewModel.filterByRatings("7.0")
                    3 -> viewModel.filterByRatings("8.0")
                    4 -> viewModel.filterByRatings("9.0")
                }
            }
            .setNeutralButton("cancel"){dialog, which->
                // hide dialog
            }
            .show()
    }

    private fun showFilterDirectorDialog(){
        // material dialog builder
        val builder = MaterialAlertDialogBuilder(requireContext())
        // title
        builder.setTitle("Search by Directors")
        // editText to take input from user
        val input = EditText(requireContext())

        // hint to display in editText
        input.hint = "Enter Director's Name"
        //input type
        input.inputType = InputType.TYPE_CLASS_TEXT
        // setting editText to builder
        builder.setView(input)

        // setting OK button and clickListener
        builder.setPositiveButton("OK"){dialog, which->
            // getting text from EditText
            val name = input.text.toString()
            // calling method to filter movies based on director's name
            viewModel.filterByDirectorName(name)
        }
        builder.setNeutralButton("cancel"){dialog, which->
            // hide dialog
        }
        // display the dialog
        builder.show()

    }


}