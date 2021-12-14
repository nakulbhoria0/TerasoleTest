package com.example.terasoletest

import com.squareup.moshi.Json

// data class for Network Response
data class Movie (
    val year:Int?,
    val title:String?="",
    val info:Info
)
data class Info(
    val directors:List<String?>,
    @field:Json(name ="release_date") val releaseDate:String?="",
    val rating:Float?,
    val genres:List<String?>,
    @field:Json(name = "image_url")val imageUrl:String?,
    val plot:String?,
    val rank:Int,
    @field:Json(name="running_time_secs")val runningTimeSec:Int,
    val actors:List<String?>
)

// mapping our every Movie object of List to DatabaseMovie and return List of DatabaseMovie
fun List<Movie>.asDatabaseModel():List<DatabaseMovie>{
    return map{
        DatabaseMovie(
            title = it.title,
            year = it.year,
            directors = toString(it.info.directors),
            releaseDate = it.info.releaseDate,
            rating = it.info.rating,
            genres = toString(it.info.genres),
            imageUrl = it.info.imageUrl,
            plot = it.info.plot,
            rank = it.info.rank,
            runningTimeSec = it.info.runningTimeSec,
            actors = toString(it.info.actors))
    }
}

// convert list to string
fun toString(list: List<String?>?): String {
    return if(list.isNullOrEmpty()){
        ""
    }else{
        list.toString()
    }
}