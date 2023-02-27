package com.putrash.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result<T: Parcelable>(
    @SerializedName("id")
    val id: Int? = 1,
    @SerializedName("page")
    val page: Int? = 1,
    @SerializedName("total_pages")
    val totalPages: Int? = 1,
    @SerializedName("total_results")
    val totalResults: Int? = 0,
    @SerializedName("results")
    val results: ArrayList<T>
) : Parcelable
