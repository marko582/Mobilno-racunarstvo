package com.example.frontend.ui.profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val username: String = "Mile Dizna",
    val memberSince: String = "May 1977",
    val moviesCount: String = "124",
    val watchlistCount: String = "45",
    val ratingsCount: String = "89",
    val isDarkMode: Boolean = false
)