package com.example.frontend.ui.watchlist

import androidx.lifecycle.ViewModel
import com.example.frontend.ui.data.MovieRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WatchlistViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState = _uiState.asStateFlow()

    private var allItems = MovieRepo.dummyWatchlist

    init {
        _uiState.update { it.copy(items = allItems) }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }

        val filtered = if (newQuery.isEmpty()) {
            allItems
        } else {
            allItems.filter { it.movie.title.contains(newQuery, ignoreCase = true) }
        }

        _uiState.update { it.copy(items = filtered) }
    }
}