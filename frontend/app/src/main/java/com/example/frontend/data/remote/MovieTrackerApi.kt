package com.example.frontend.data.remote

import com.example.frontend.data.remote.dto.AuthResponseDto
import com.example.frontend.data.remote.dto.CommentDto
import com.example.frontend.data.remote.dto.CommentRequestDto
import com.example.frontend.data.remote.dto.LoginRequestDto
import com.example.frontend.data.remote.dto.MovieDto
import com.example.frontend.data.remote.dto.PageResponse
import com.example.frontend.data.remote.dto.RatingRequestDto
import com.example.frontend.data.remote.dto.RegisterRequestDto
import com.example.frontend.data.remote.dto.UserDto
import com.example.frontend.data.remote.dto.UserRatingDto
import com.example.frontend.data.remote.dto.UserUpdateRequestDto
import com.example.frontend.data.remote.dto.WatchlistItemDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieTrackerApi {
    // Auth
    @POST("/api/auth/login")
    suspend fun login(@Body body: LoginRequestDto): AuthResponseDto

    @POST("/api/auth/register")
    suspend fun register(@Body body: RegisterRequestDto): UserDto

    // Movies
    @GET("/api/movies")
    suspend fun listMovies(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): PageResponse<MovieDto>

    @GET("/api/movies/search")
    suspend fun searchMovies(
        @Query("title") title: String?,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): PageResponse<MovieDto>

    @GET("/api/movies/{id}")
    suspend fun getMovie(@Path("id") id: Long): MovieDto

    // Comments
    @GET("/api/comments/movie/{movieId}")
    suspend fun listComments(@Path("movieId") movieId: Long): List<CommentDto>

    @POST("/api/comments/{movieId}")
    suspend fun addComment(@Path("movieId") movieId: Long, @Body body: CommentRequestDto): CommentDto

    @PUT("/api/comments/{commentId}")
    suspend fun updateComment(@Path("commentId") commentId: Long, @Body body: CommentRequestDto): CommentDto

    @DELETE("/api/comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: Long)

    // Watchlist
    @GET("/api/watchlist")
    suspend fun listWatchlist(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): PageResponse<WatchlistItemDto>

    @POST("/api/watchlist/{movieId}")
    suspend fun addToWatchlist(@Path("movieId") movieId: Long): WatchlistItemDto

    @DELETE("/api/watchlist/{movieId}")
    suspend fun removeFromWatchlist(@Path("movieId") movieId: Long)

    // Ratings
    @GET("/api/ratings/me")
    suspend fun myRatings(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): PageResponse<UserRatingDto>

    @POST("/api/ratings/{movieId}")
    suspend fun rateMovie(@Path("movieId") movieId: Long, @Body body: RatingRequestDto): UserRatingDto

    // User
    @GET("/api/users/me")
    suspend fun me(): UserDto

    @PUT("/api/users/me")
    suspend fun updateMe(@Body body: UserUpdateRequestDto): UserDto
}

