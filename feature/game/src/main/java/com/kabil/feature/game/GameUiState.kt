package com.kabil.feature.game

import com.kabil.core.ui.LetterFeedback

sealed interface GameUiState {
    data class Active(
        val wordToGuess: String,
        val guesses: List<List<LetterFeedback>> = emptyList()        // Current guess the player is typing
    ) : GameUiState

    data class GameWon(
        val guesses: List<List<LetterFeedback>> = emptyList()
    ) : GameUiState

    data class GameOver(
        val guesses: List<List<LetterFeedback>> = emptyList(),
    ) : GameUiState

    data object Loading : GameUiState
}