package com.example.frontend.ui.account_settings

data class AccountSettingsUiState(
    val isLoading: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val username: String = "",
    val email: String = "",
    val error: String? = null,
    val isSaved: Boolean = false
)

