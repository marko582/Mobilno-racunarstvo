package com.example.frontend.ui.rated_movies

import com.example.frontend.ui.data.Movie

data class RatedMoviesUiState(
    val isLoading: Boolean = false,
    val items: List<RatedItemData> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

data class RatedItemData(
    val movie: Movie,
    val rating: Int
)