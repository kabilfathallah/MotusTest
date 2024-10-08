package com.kabil.core.data

import com.kabil.core.database.datasource.LocalDatasource
import com.kabil.core.database.model.WordEntity
import com.kabil.core.network.datasource.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.never
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class WordsRepositoryImplTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDatasource

    private lateinit var repository: WordsRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = mock()
        localDataSource = mock()

        repository = WordsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun testGetWordsFromRemoteAndSavesToLocalWhenEmpty() = runTest {
        val remoteWords = listOf("HELLO", "WORLD")
        `when`(localDataSource.getAllWords()).thenReturn(
            flowOf(
                emptyList()
            )
        )

        `when`(remoteDataSource.getWords()).thenReturn(remoteWords)
        repository.getWords()
        verify(remoteDataSource).getWords()
        verify(localDataSource).insertAll(remoteWords)
    }

    @Test
    fun testGetWordsCalledFromLocal() = runTest {
        val localWords = listOf(WordEntity(text = "HELLO"), WordEntity(text = "WORLD"))
        `when`(localDataSource.getAllWords()).thenReturn(flowOf(localWords))

        val result = repository.getWords()

        verify(
            remoteDataSource,
            never()
        ).getWords()
        assertEquals(
            Result.success(listOf("HELLO", "WORLD")),
            result
        )
    }

    @Test
    fun testGetRandomReturnsFromLocal() = runTest {
        val word = WordEntity(text = "HELLO")
        `when`(localDataSource.getRandomWord()).thenReturn(flowOf(word))

        val result = repository.getRandomWord()

        assertEquals("HELLO", result.first())
    }
}