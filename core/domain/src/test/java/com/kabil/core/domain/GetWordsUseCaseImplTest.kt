package com.kabil.core.domain

import app.cash.turbine.test
import com.kabil.core.common.Result
import com.kabil.core.domain.repository.WordsRepository
import com.kabil.core.domain.usecase.GetWordsUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetWordsUseCaseImplTest {

    private lateinit var getWordsUseCase: GetWordsUseCaseImpl
    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setup() {
        wordsRepository = mock()
        getWordsUseCase = GetWordsUseCaseImpl(wordsRepository)
    }

    @Test
    fun testSuccess() = runTest {
        val listOfWords = flowOf(listOf("HELLO", "WORLD"))
        Mockito.`when`(wordsRepository.getWords()).doReturn(listOfWords)

        getWordsUseCase.invoke().test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Success(listOfWords.first()), awaitItem())
            awaitComplete()
        }

    }

    @Test
    fun testError() = runTest {
        val exception = Exception("Test exception")

        Mockito.`when`(wordsRepository.getWords()).doAnswer { throw exception }

        getWordsUseCase.invoke().test {
            assertEquals(Result.Loading, awaitItem())
            assertEquals(Result.Error(exception), awaitItem())
            awaitComplete()
        }

    }


}