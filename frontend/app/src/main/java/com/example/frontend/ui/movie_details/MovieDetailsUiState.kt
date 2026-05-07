package com.example.frontend.ui.movie_details

import com.example.frontend.ui.data.Movie
import com.example.frontend.ui.data.Comment

data class MovieDetailsUiState(
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String? = null,
    val isInWatchlist: Boolean = false,
    val userRating: Int = 0,
    val comments: List<Comment> = emptyList()
)