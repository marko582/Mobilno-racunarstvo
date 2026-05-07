package com.example.frontend.data.remote.dto

data class PageResponse<T>(
    val content: List<T> = emptyList(),
    val totalElements: Long? = null,
    val totalPages: Int? = null,
    val number: Int? = null,
    val size: Int? = null
)

data class MovieDto(
    val id: Long,
    val title: String,
    val description: String?,
    val genre: String?,
    val releaseYear: Int?,
    val duration: Int?,
    val imageUrl: String?,
    val averageRating: Double?,
    val numberOfRatings: Long?
)

data class WatchlistItemDto(
    val watchlistId: Long,
    val dateAdded: String,
    val movie: MovieDto
)

data class UserRatingDto(
    val ratingId: Long,
    val stars: Int,
    val createdAt: String,
    val movie: MovieDto
)

data class CommentDto(
    val id: Long,
    val username: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String?
)

data class UserDto(
    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val username: String,
    val email: String?
)

data class AuthResponseDto(
    val accessToken: String,
    val tokenType: String?,
    val expiresInMs: Long?
)

