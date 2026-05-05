package com.example.frontend.ui.rated_movies

import androidx.lifecycle.ViewModel
import com.example.frontend.ui.data.MovieRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RatedMoviesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RatedMoviesUiState())
    val uiState = _uiState.asStateFlow()

    private var allRatedItems = MovieRepo.dummyRatedMovies

    init {
        _uiState.update { it.copy(items = allRatedItems) }
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