package com.putrash.data

import com.putrash.data.model.Movie
import com.putrash.data.model.Review
import com.putrash.data.model.Video
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")
        apiKey: String,
        @Query("page")
        page: Int,
    ) : Response<Result<Movie>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String,
        @Query("page")
        page: Int,
    ) : Response<Result<Movie>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")
        id: Int,
        @Query("api_key")
        apiKey: String,
    ) : Response<Movie>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id")
        id: Int,
        @Query("api_key")
        apiKey: String,
        @Query("page")
        page: Int,
    ) : Response<Result<Review>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id")
        id: Int,
        @Query("api_key")
        apiKey: String,
    ) : Response<Result<Video>>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key")
        apiKey: String,
    ) : Response<ResultGenre>
}