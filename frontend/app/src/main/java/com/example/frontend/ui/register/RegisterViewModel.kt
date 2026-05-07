package com.example.frontend.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onFirstNameChange(v: String) { _uiState.update { it.copy(firstName = v, error = null) } }
    fun onLastNameChange(v: String) { _uiState.update { it.copy(lastName = v, error = null) } }
    fun onUsernameChange(v: String) { _uiState.update { it.copy(username = v, error = null) } }
    fun onEmailChange(v: String) { _uiState.update { it.copy(email = v, error = null) } }
    fun onPasswordChange(v: String) { _uiState.update { it.copy(password = v, error = null) } }
    fun onConfirmPasswordChange(v: String) { _uiState.update { it.copy(confirmPassword = v, error = null) } }

    fun onRegisterClick() {
        val s = _uiState.value

        if (s.firstName.isBlank() || s.lastName.isBlank() || s.username.isBlank() || s.email.isBlank() || s.password.isBlank() || s.confirmPassword.isBlank()) {
            _uiState.update { it.copy(error = "All fields are necessary") }
            return
        }

        if (s.password != s.confirmPassword) {
            _uiState.update { it.copy(error = "Password do not match") }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                AppGraph.repository.register(
                    firstName = s.firstName,
                    lastName = s.lastName,
                    username = s.username,
                    email = s.email,
                    password = s.password
                )
                _uiState.update { it.copy(isLoading = false, isRegisterSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Register failed") }
            }
        }
    }
}