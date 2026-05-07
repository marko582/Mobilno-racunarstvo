package com.example.frontend.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WatchlistViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WatchlistUiState())
    val uiState = _uiState.asStateFlow()

    private var allItems = emptyList<WatchlistItemData>()

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val items = AppGraph.repository.listWatchlist()
                allItems = items
                _uiState.update { it.copy(isLoading = false, items = items) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load watchlist") }
            }
        }
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