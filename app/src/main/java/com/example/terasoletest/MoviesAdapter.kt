package com.example.terasoletest

import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Database
import coil.api.load

class MoviesViewHolder(view: View):RecyclerView.ViewHolder(view){
    // initializing all views from received view param
    val imageView:ImageView = view.findViewById(R.id.movieImageView)
    val titleTextView:TextView = view.findViewById(R.id.titleTextView)
    val yearTextView:TextView = view.findViewById(R.id.yearTextView)
    val ratingTextView:TextView = view.findViewById(R.id.ratingTextView)

    fun bind(movie: DatabaseMovie){
        // bind data to views from received movie param
        titleTextView.text = movie.title
        yearTextView.text = (movie.year ?: "not found").toString()
        ratingTextView.text = (movie.rating ?: "No Ratings").toString()

        // load image using coil library
        imageView.load(movie.imageUrl)

    }
}

// movie adapter takes in 2 params
// 1st is list of DatabaseMovie
// 2nd is a clickHandler Function
// to respond onClick in Fragment
class MoviesAdapter(val list: List<DatabaseMovie>,
    val clickHandler: (DatabaseMovie) -> Unit
):RecyclerView.Adapter<MoviesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        // inflating view from layout
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_layout,parent, false )

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        // bind data to viewHolder
        val currentMovie = list[position]
        holder.bind(currentMovie)

        // ocClickListener
        holder.itemView.setOnClickListener {
            clickHandler(currentMovie)
        }
    }

    override fun getItemCount(): Int {
        // return list.size to tell how many items are there in the list
        return list.size
    }
}