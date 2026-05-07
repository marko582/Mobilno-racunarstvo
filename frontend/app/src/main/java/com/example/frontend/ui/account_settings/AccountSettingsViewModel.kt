package com.example.frontend.ui.account_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend.data.AppGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountSettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AccountSettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSaved = false) }
            try {
                val me = AppGraph.api.me()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        firstName = me.firstName.orEmpty(),
                        lastName = me.lastName.orEmpty(),
                        username = me.username,
                        email = me.email.orEmpty()
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Failed to load profile") }
            }
        }
    }

    fun onFirstNameChange(v: String) = _uiState.update { it.copy(firstName = v, error = null, isSaved = false) }
    fun onLastNameChange(v: String) = _uiState.update { it.copy(lastName = v, error = null, isSaved = false) }
    fun onUsernameChange(v: String) = _uiState.update { it.copy(username = v, error = null, isSaved = false) }
    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v, error = null, isSaved = false) }

    fun onSave() {
        val s = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSaved = false) }
            try {
                AppGraph.repository.updateMe(
                    firstName = s.firstName.ifBlank { null },
                    lastName = s.lastName.ifBlank { null },
                    username = s.username.ifBlank { null },
                    email = s.email.ifBlank { null }
                )
                _uiState.update { it.copy(isLoading = false, isSaved = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, error = e.message ?: "Save failed") }
            }
        }
    }
}

