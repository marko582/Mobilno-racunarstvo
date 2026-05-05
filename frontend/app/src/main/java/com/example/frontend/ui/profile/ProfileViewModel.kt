package com.example.frontend.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            delay(800)
            _uiState.update {
                it.copy(
                    username = "Mile Dizna",
                    memberSince = "May 1977",
                    moviesCount = "124",
                    watchlistCount = "45",
                    ratingsCount = "89",
                    isLoading = false
                )
            }
        }
    }

    fun toggleTheme(onToggle: () -> Unit) {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
        onToggle()
    }
}