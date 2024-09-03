package com.kabil.feature.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kabil.core.ui.LetterFeedback
import com.kabil.core.ui.LetterStatus
import com.kabil.core.ui.WordInput
import com.kabil.core.ui.WordOutput

@Composable
fun GameRoute(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel = hiltViewModel(),
    navigateBack: (() -> Unit)?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    GameScreen(
        modifier = modifier,
        uiState = uiState,
        guessWord = viewModel::submitGuess,
        nextGame = viewModel::nextGame,
        navigateBack = navigateBack
    )
}

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    uiState: GameUiState,
    guessWord: ((String) -> Unit)? = null,
    nextGame: (() -> Unit)? = null,
    navigateBack: (() -> Unit)? = null
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = {
            navigateBack?.invoke()
        }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            WordsOutputs(
                outputs = when (uiState) {
                    is GameUiState.Active -> uiState.guesses
                    is GameUiState.GameOver -> uiState.guesses
                    is GameUiState.GameWon -> uiState.guesses
                    else -> emptyList()
                }
            )

            when (uiState) {

                is GameUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is GameUiState.Active -> {
                    Spacer(modifier = Modifier.height(16.dp))
                    WordInput(
                        word = uiState.wordToGuess,
                        onValueChange = guessWord
                    )
                }

                is GameUiState.GameOver -> {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        nextGame?.invoke()
                    }) {
                        Text(text = "Try Again")

                    }
                }

                is GameUiState.GameWon -> {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = { nextGame?.invoke() }) {
                        Text(text = "Next Game")
                    }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun WordsOutputs(
    outputs: List<List<LetterFeedback>>
) {
    Column {
        outputs.forEach { guess ->
            WordOutput(lettersResult = guess)
        }
    }

}

@Preview
@Composable
private fun PreviewGameScreenLoading() {
    GameScreen(uiState = GameUiState.Loading) {}
}

@Preview
@Composable
private fun PreviewGameScreenIsActive() {
    GameScreen(
        uiState = GameUiState.Active(
            wordToGuess = "HELLO",
            guesses = listOf(
                listOf(
                    LetterFeedback('H', LetterStatus.CORRECT),
                    LetterFeedback('E', LetterStatus.CORRECT),
                    LetterFeedback('L', LetterStatus.CORRECT),
                    LetterFeedback('L', LetterStatus.CORRECT),
                    LetterFeedback('O', LetterStatus.CORRECT),
                )
            )
        )
    ) {
    }
}

@Preview
@Composable
private fun PreviewGameScreenGameWon() {
    GameScreen(uiState = GameUiState.GameWon()) {
    }
}

@Preview
@Composable
private fun PreviewGameScreenGameLost() {
    GameScreen(
        uiState = GameUiState.GameOver(
            guesses = listOf(
                listOf(
                    LetterFeedback('H', LetterStatus.CORRECT),
                    LetterFeedback('E', LetterStatus.CORRECT),
                    LetterFeedback('L', LetterStatus.CORRECT),
                    LetterFeedback('L', LetterStatus.CORRECT),
                    LetterFeedback('O', LetterStatus.CORRECT),
                )
            )
        )
    ) {

    }

}