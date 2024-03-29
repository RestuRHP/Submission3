package net.restu.submission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesItem(
    var title: String,
    var overview: String,
    var vote_average: String,
    var release_date: String,
    var language: String,
    var poster: String,
    var backdrop:String
) : Parcelable

@Parcelize
class MovieItems : ArrayList<MoviesItem>(), Parcelable