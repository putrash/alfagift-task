package com.putrash.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("key")
    val key: String? = "",
    @SerializedName("site")
    val site: String? = "",
    @SerializedName("official")
    val official: Boolean? = false,
    @SerializedName("published_at")
    val publishedAt: String? = "",
) : Parcelable
