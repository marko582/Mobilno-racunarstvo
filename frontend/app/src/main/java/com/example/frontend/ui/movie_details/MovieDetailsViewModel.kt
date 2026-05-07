package com.example.frontend.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Long?
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState.asStateFlow()

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        if (movieId == null) {
            _uiState.update { it.copy(error = "Movie ID is missing") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val movie = AppGraph.repository.getMovie(movieId)
                val comments = AppGraph.repository.listComments(movieId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movie = movie,
                        comments = comments
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load details") }
            }
        }
    }

    fun toggleWatchlist() {
        val id = movieId ?: return
        viewModelScope.launch {
            val target = !_uiState.value.isInWatchlist
            try {
                AppGraph.repository.toggleWatchlist(movieId = id, add = target)
                _uiState.update { it.copy(isInWatchlist = target) }
                // ensure we show fresh comments/details if needed later
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Watchlist update failed") }
            }
        }
    }

    fun updateRating(newRating: Int) {
        val id = movieId ?: return
        viewModelScope.launch {
            try {
                val stars = AppGraph.repository.rate(movieId = id, stars = newRating)
                _uiState.update { it.copy(userRating = stars) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Rating failed") }
            }
        }
    }

    fun postComment(text: String) {
        if (text.isBlank()) return
        val id = movieId ?: return

        viewModelScope.launch {
            try {
                val created = AppGraph.repository.addComment(movieId = id, text = text)
                _uiState.update { it.copy(comments = listOf(created) + it.comments) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Comment failed") }
            }
        }
    }
}