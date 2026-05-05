package com.example.frontend.ui.movie_list

import com.example.frontend.ui.data.Movie

data class MovieListUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val errorMessage: String? = null
)