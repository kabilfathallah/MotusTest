package com.kabil.feature.welcome

import app.cash.turbine.test
import com.kabil.core.common.Result
import com.kabil.core.domain.usecase.GetWordsUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class WelcomeViewModelTest {

    private lateinit var getWordsUseCase: GetWordsUseCaseImpl
    private lateinit var viewModel: WelcomeViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() {
        getWordsUseCase = mock()
    }

    @Test
    fun stateLoading() = runTest {
        val useCase = mock<GetWordsUseCaseImpl>()
        viewModel = WelcomeViewModel(useCase)
        assertEquals(WelcomeUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun testSuccess() = runTest {
        val words = kotlin.Result.success(listOf("Hello", "World"))
        val flow = flow {
            emit(Result.Loading)
            emit(Result.Success(words))
        }
        `when`(getWordsUseCase()).thenReturn(words)

        viewModel = WelcomeViewModel(getWordsUseCase)
        viewModel.uiState.test {
            assertEquals(WelcomeUiState.Loading, awaitItem())
            advanceUntilIdle()
            assertEquals(WelcomeUiState.Success, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testError() = runTest {

        val exception = Exception("Test exception")
        val flow = flowOf(Result.Error(exception))
        `when`(getWordsUseCase()).thenAnswer { throw exception }

        viewModel = WelcomeViewModel(getWordsUseCase)

        viewModel.uiState.test {
            assertEquals(WelcomeUiState.Loading, awaitItem())
            assertEquals(WelcomeUiState.Error(exception), awaitItem())
            cancelAndIgnoreRemainingEvents()
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

}