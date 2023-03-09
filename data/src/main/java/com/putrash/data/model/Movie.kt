package com.putrash.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id: Int = 1,
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("adult")
    val adult: Boolean? = false,
    @SerializedName("overview")
    val overview: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("runtime")
    val runtime: Int? = 0,
    @SerializedName("genre_ids")
    val genreIds: ArrayList<Int> = arrayListOf(),
    @SerializedName("original_title")
    val originalTitle: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("backdrop_path")
    val backdropPath: String? = "",
    @SerializedName("video")
    val video: Boolean? = false,
    @SerializedName("vote_average")
    val voteAverage: Float? = 0.0f,
    @SerializedName("genres")
    val genres: List<Genre>? = arrayListOf()
) : Parcelable