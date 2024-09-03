package com.kabil.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kabil.core.database.dao.WordsDao
import com.kabil.core.database.datasource.LocalDatasourceImpl
import com.kabil.core.database.model.WordEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class LocalDatasourceTest {

    private lateinit var localDatasourceImpl: LocalDatasourceImpl
    private lateinit var wordsDao: WordsDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        wordsDao = appDatabase.wordsDao()

        localDatasourceImpl = LocalDatasourceImpl(wordsDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeWordsAndReadInList() = runBlocking {
        val listOfWords = listOf("HELLO", "WORLD", "ANDROID")
        localDatasourceImpl.insertAll(listOfWords)
        assertEquals(localDatasourceImpl.getAllWords().first(),
            listOf(
                WordEntity(1,"HELLO"),
                WordEntity(2,"WORLD"),
                WordEntity(3,"ANDROID"),
                )
        )
        assertEquals(
            localDatasourceImpl.getAllWords().first().map { it.text },
            listOfWords
        )
    }

    @Test
    fun testGetRandomWord() = runBlocking {
        val listOfWords = listOf("ANDROID")
        localDatasourceImpl.insertAll(listOfWords)
        assertEquals(localDatasourceImpl.getRandomWord().first().text, "ANDROID")
    }

    @Test
    fun testWordExist() = runBlocking {
        val listOfWords = listOf("HELLO", "WORLD", "ANDROID")
        localDatasourceImpl.insertAll(listOfWords)
        assert(localDatasourceImpl.checkIfExist("HELLO"))
        assert(!localDatasourceImpl.checkIfExist("THERE"))

    }


}