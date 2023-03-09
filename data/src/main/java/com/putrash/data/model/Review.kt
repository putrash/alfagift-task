package com.putrash.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("author")
    val author: String? = "",
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("created_at")
    val createdAt: String? = "",
    @SerializedName("updated_at")
    val updatedAt: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("author_details")
    val authorDetails: AuthorDetails = AuthorDetails(),
) : Parcelable {
    @Parcelize
    data class AuthorDetails(
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("username")
        val username: String? = "",
        @SerializedName("avatar_path")
        val avatarPath: String? = "",
        @SerializedName("rating")
        val rating: Float? = 0.0f,
    ): Parcelable
}
