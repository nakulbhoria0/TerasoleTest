package com.example.terasoletest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.terasoletest.databinding.FragmentMovieDetailsBinding


class MovieDetailsFragment : Fragment() {

    // binding object to bind views
    private lateinit var binding:FragmentMovieDetailsBinding

    // getting arguments passed by MoviesListViewFragment
    private val args: MovieDetailsFragmentArgs by navArgs()

    // initializing viewModel
    private val viewModel: MoviesDetailsViewModel by lazy {
        val activity = requireNotNull(this.activity){}
        ViewModelProvider(this, MoviesDetailsViewModel.Factory(activity.application))
            .get(MoviesDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initializing binding
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        // sending arguments to viewModel to use even after rotation/state changes
        viewModel.setArguments(args)

        // binding data to views
        bindData()

        return binding.root
    }


    // Bind Data to views
    private fun bindData() {
        // DatabaseMovie
        val databaseMovie = viewModel.args.databaseMovie

        // Movie Poster
        binding.movieImageView.load(databaseMovie.imageUrl)

        // Title
        binding.titleTextView.text =
                getString(R.string.pre_text_for_details,
                "Title", databaseMovie.title ?: "not found")

        // Year
        binding.yearTextView.text =
            getString(R.string.pre_text_for_details,
                "Year", databaseMovie.year ?: "not found")

        // Actors
        binding.actorsTextView.text =
        getString(
            R.string.pre_text_for_details,
            "Actors", databaseMovie.actors ?: "not found"
        )

        // Directors
        binding.directorsTextView.text = getString(
            R.string.pre_text_for_details,
            "Directors", databaseMovie.directors ?: "not found"
        )

        // Plot
        binding.plotTextView.text =
            getString(
                R.string.pre_text_for_details,
                "Plot", databaseMovie.plot ?: "not found"
            )

        // Running Time
        val runningTime =
        getString(
            R.string.pre_text_for_details,
            "Running Time Sec", databaseMovie.runningTimeSec ?: "not found"
        )
        binding.runningTimeTextView.text = (runningTime)

        // Rating
        val rating =
        getString(
            R.string.pre_text_for_details,
            "Ratings", databaseMovie.rating ?: "not found"
        )
        binding.ratingTextView.text = rating

        // Rank
        val rank =
        getString(
            R.string.pre_text_for_details,
            "Rank", databaseMovie.rank ?: "not found"
        )
        binding.rankTextView.text = rank
    }

}