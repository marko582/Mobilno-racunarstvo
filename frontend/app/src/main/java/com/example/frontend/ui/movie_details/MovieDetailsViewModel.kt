package com.example.frontend.ui.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.ui.data.MovieRepo // Pretpostavka gde su ti podaci
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: String?
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

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000)

            val foundMovie = MovieRepo.dummyMovies.find { it.id == movieId }

            if (foundMovie != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movie = foundMovie,
                        isInWatchlist = false,
                        userRating = 0
                    )
                }
            } else {
                _uiState.update {
                    it.copy(isLoading = false, error = "Movie not found")
                }
            }
        }
    }

    fun toggleWatchlist() {
        _uiState.update { currentState ->
            currentState.copy(isInWatchlist = !currentState.isInWatchlist)
        }
    }

    fun updateRating(newRating: Int) {
        _uiState.update { it.copy(userRating = newRating) }
    }

    fun postComment(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            //TODO
        }
    }
}