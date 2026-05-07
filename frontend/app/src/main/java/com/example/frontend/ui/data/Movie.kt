package com.example.frontend.ui.data

data class Movie(
    val id: Long,
    val title: String,
    val imageUrl: String? = null,
    val description: String? = null,
    val genre: String? = null,
    val releaseYear: Int? = null,
    val duration: Int? = null,
    val averageRating: Double? = null,
    val numberOfRatings: Long? = null
)