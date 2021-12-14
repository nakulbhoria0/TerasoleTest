package com.example.terasoletest

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize

// Model class for Database
// Parcelable because we are passing it as argument in MoviesDetailsFragment
// Entity of our database table name is "movie"
@Entity(tableName = "movie")
@Parcelize
data class DatabaseMovie(
    val year:Int?,
    val title:String?,
    val directors: String?,
    val releaseDate:String?,
    val rating:Float?,
    val genres: String?,
    val imageUrl:String?,
    val plot:String?,
    @PrimaryKey(autoGenerate = false)
    val rank:Int?,
    val runningTimeSec:Int?,
    val actors: String?
):Parcelable

