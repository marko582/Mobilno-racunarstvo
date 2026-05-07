package com.example.frontend.ui.profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val username: String = "",
    val email: String = "",
    val watchlistCount: String = "0",
    val ratingsCount: String = "0",
    val error: String? = null,
    val isDarkMode: Boolean = false
)