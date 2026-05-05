package com.example.frontend.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.update { it.copy(email = newValue, error = null) }
    }

    fun onPasswordChange(newValue: String) {
        _uiState.update { it.copy(password = newValue, error = null) }
    }

    fun onLoginClick() {
        val currentEmail = _uiState.value.email
        val currentPassword = _uiState.value.password

        if (currentEmail.isBlank() || currentPassword.isBlank()) {
            _uiState.update { it.copy(error = "Please fill both fields") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1500)

            if (currentEmail == "admin" && currentPassword == "admin") {
                _uiState.update { it.copy(isLoading = false, isLoginSuccess = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Wrong email or password") }
            }
        }
    }
}