package com.example.frontend.ui.rated_movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RatedMoviesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RatedMoviesUiState())
    val uiState = _uiState.asStateFlow()

    private var allRatedItems = emptyList<RatedItemData>()

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val items = AppGraph.repository.myRatings()
                allRatedItems = items
                _uiState.update { it.copy(isLoading = false, items = items) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load ratings") }
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _uiState.update { it.copy(searchQuery = newQuery) }

        val filtered = if (newQuery.isEmpty()) {
            allRatedItems
        } else {
            allRatedItems.filter { it.movie.title.contains(newQuery, ignoreCase = true) }
        }

        _uiState.update { it.copy(items = filtered) }
    }
}