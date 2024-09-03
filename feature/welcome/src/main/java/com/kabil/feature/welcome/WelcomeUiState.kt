package com.kabil.feature.welcome

sealed interface WelcomeUiState {
    data class Error(val it: Throwable) : WelcomeUiState
    data object Loading : WelcomeUiState
    data object Success : WelcomeUiState
}