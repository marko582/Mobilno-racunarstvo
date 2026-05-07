package com.example.frontend.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        reload()
    }

    fun reload() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val me = AppGraph.repository.me()
                val watchlist = AppGraph.repository.listWatchlist()
                val ratings = AppGraph.repository.myRatings()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        username = me.username,
                        email = me.email.orEmpty(),
                        watchlistCount = watchlist.size.toString(),
                        ratingsCount = ratings.size.toString()
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load profile") }
            }
        }
    }

    fun toggleTheme(onToggle: () -> Unit) {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
        onToggle()
    }
}