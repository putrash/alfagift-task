package com.putrash.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.putrash.data.model.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultGenre(
    @SerializedName("genres")
    val results: ArrayList<Genre>
) : Parcelable
