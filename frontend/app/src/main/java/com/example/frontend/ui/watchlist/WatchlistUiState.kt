package com.example.frontend.ui.watchlist

import com.example.frontend.ui.data.Movie

data class WatchlistUiState(
    val isLoading: Boolean = false,
    val items: List<WatchlistItemData> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

data class WatchlistItemData(
    val movie: Movie,
    val dateAdded: String
)