package com.kabil.feature.game

import app.cash.turbine.test
import com.kabil.core.domain.usecase.GetRandomWordUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock


@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {

    private lateinit var viewModel: GameViewModel
    private lateinit var getRandomWordUseCase: GetRandomWordUseCaseImpl

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @Before
    fun setup() {
        getRandomWordUseCase = mock()
    }

    @Test
    fun stateLoading() = runTest {
        Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("HELLO"))
        viewModel = GameViewModel(getRandomWordUseCase)
        viewModel.uiState.test {
            assertEquals(GameUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun firstActive() = runTest {
        Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("HELLO"))
        viewModel = GameViewModel(getRandomWordUseCase)
        viewModel.uiState.test {
            assertEquals(GameUiState.Loading, awaitItem())
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO"
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun firstGoodGuessActive() = runTest {
        Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("HELLO"))
        viewModel = GameViewModel(getRandomWordUseCase)
        viewModel.uiState.test {
            assertEquals(GameUiState.Loading, awaitItem())
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO"
                ), awaitItem()
            )
            viewModel.submitGuess("HELLO")
            assertEquals(
                GameUiState.GameWon(
                    guesses = goodHelloTry
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun firstGoodGuessAndGoToNextWordActive() = runTest {
        Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("HELLO"))
        viewModel = GameViewModel(getRandomWordUseCase)
        viewModel.uiState.test {
            // initial state Loading
            assertEquals(GameUiState.Loading, awaitItem())
            // getting the word and changing the state
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO"
                ), awaitItem()
            )
            //submiting the guess
            viewModel.submitGuess("HELLO")
            //game Won
            assertEquals(
                GameUiState.GameWon(
                    guesses = goodHelloTry
                ), awaitItem()
            )

            //we change the word
            Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("WORLD"))
            //going the next game
            viewModel.nextGame()
            viewModel.uiState.test {
                advanceUntilIdle()
                assertEquals(GameUiState.Loading, awaitItem())

                // getting the word and changing the word
                // we should have an empty list of guesses
                assertEquals(
                    GameUiState.Active(
                        wordToGuess = "WORLD",
                        guesses = emptyList(),
                    ), awaitItem()
                )

            }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun firstWrongGuess() = runTest {
        Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("HELLO"))
        viewModel = GameViewModel(getRandomWordUseCase)
        viewModel.uiState.test {
            assertEquals(GameUiState.Loading, awaitItem())
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO"
                ), awaitItem()
            )
            viewModel.submitGuess("HEIIO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = firstWrongTry
                ), awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testGameOver() = runTest {
        Mockito.`when`(getRandomWordUseCase()).doReturn(flowOf("HELLO"))
        viewModel = GameViewModel(getRandomWordUseCase)
        viewModel.uiState.test {
            assertEquals(GameUiState.Loading, awaitItem())
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO"
                ), awaitItem()
            )
            //first try
            viewModel.submitGuess("HEIIO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = firstWrongTry
                ), awaitItem()
            )
            //second try
            viewModel.submitGuess("HEFFO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = secondWrongTry
                ), awaitItem()
            )

            //third try

            viewModel.submitGuess("HEBBO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = thirdWrongTry
                ), awaitItem()
            )

            //fourth try

            viewModel.submitGuess("HEMMO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = fourthWrongTry
                ), awaitItem()
            )

            //fifth try

            viewModel.submitGuess("HEPPO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = fifthWrongTry
                ), awaitItem()
            )

            //sixth try

            viewModel.submitGuess("HEYYO")
            assertEquals(
                GameUiState.Active(
                    wordToGuess = "HELLO",
                    guesses = sixtWrongTry
                ), awaitItem()
            )

            //seventh try is GameOver and we add the correct answer

            viewModel.submitGuess("HEAAO")
            assertEquals(
                GameUiState.GameOver(
                    guesses = lastWrongTry
                ), awaitItem()
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

}

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule constructor(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}