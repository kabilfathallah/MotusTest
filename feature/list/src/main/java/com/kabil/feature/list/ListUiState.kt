package com.kabil.feature.list

sealed interface ListUiState {
    data object Loading : ListUiState
    data class Success(val words: List<String>) : ListUiState
}