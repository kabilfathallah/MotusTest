package com.kabil.core.domain

import app.cash.turbine.test
import com.kabil.core.domain.repository.WordsRepository
import com.kabil.core.domain.usecase.GetRandomWordUseCaseImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GetRandomWordUseCaseImplTest {

    private lateinit var getRandomWordsUseCase: GetRandomWordUseCaseImpl
    private lateinit var wordsRepository: WordsRepository

    @Before
    fun setup() {
        wordsRepository = mock()
        getRandomWordsUseCase = GetRandomWordUseCaseImpl(wordsRepository)
    }

    @Test
    fun testSuccess() = runTest {
        Mockito.`when`(wordsRepository.getRandomWord())
            .doReturn(flowOf("HELLO"))

        getRandomWordsUseCase().test {
            assertEquals("HELLO", awaitItem())
            awaitComplete()
        }

    }
}