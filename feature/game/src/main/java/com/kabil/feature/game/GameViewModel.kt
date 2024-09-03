package com.kabil.feature.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabil.core.domain.usecase.GetRandomWordUseCase
import com.kabil.core.domain.usecase.GetRandomWordUseCaseImpl
import com.kabil.core.ui.LetterFeedback
import com.kabil.core.ui.LetterStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val MAX_ATTEMPTS = 7

@HiltViewModel
class GameViewModel @Inject constructor(
    val getRandomWordUseCase: GetRandomWordUseCaseImpl
) : ViewModel() {


    private val _uiState = MutableStateFlow<GameUiState>(GameUiState.Loading)
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val guesses = mutableListOf<List<LetterFeedback>>()
    private var mysteryWord = ""

    init {

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mysteryWord = getRandomWordUseCase()
            }

            _uiState.update {
                GameUiState.Active(
                    wordToGuess = mysteryWord
                )
            }
        }


    }


    fun submitGuess(guess: String) {
        val feedback = evaluateGuess(guess)
        if (feedback.size == mysteryWord.length)
            guesses.add(feedback)
        val remainingAttempts = MAX_ATTEMPTS - guesses.size

        if (guess == mysteryWord) {
            _uiState.update { GameUiState.GameWon(guesses) }
        } else if (remainingAttempts <= 0) {
            guesses.add(evaluateGuess(mysteryWord))
            _uiState.update {
                GameUiState.GameOver(
                    guesses = guesses
                )
            }
        } else {
            _uiState.update {
                GameUiState.Active(
                    wordToGuess = mysteryWord,
                    guesses = guesses.toList()
                )
            }
        }
    }

    private fun evaluateGuess(guess: String): List<LetterFeedback> {
        if (guess.length < mysteryWord.length) return emptyList()
        return guess.mapIndexed { index, letter ->
            val status = when (letter) {
                mysteryWord[index] -> LetterStatus.CORRECT
                in mysteryWord -> LetterStatus.PRESENT
                else -> LetterStatus.ABSENT
            }
            LetterFeedback(letter, status)
        }
    }

    fun nextGame() {
        _uiState.update { GameUiState.Loading }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mysteryWord = getRandomWordUseCase()
            }
            guesses.clear()
            _uiState.update {
                GameUiState.Active(
                    wordToGuess = mysteryWord,
                    guesses = emptyList()
                )
            }
        }

    }

}
