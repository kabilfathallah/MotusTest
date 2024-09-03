package com.kabil.core.database.datasource

import com.kabil.core.database.dao.WordsDao
import com.kabil.core.database.model.WordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDatasourceImpl @Inject constructor(private val wordsDao: WordsDao) : LocalDatasource {
    override suspend fun insertAll(words: List<String>) {
        wordsDao.insertAll(*words.map { WordEntity(text = it) }.toTypedArray())
    }

    override fun getAllWords(): Flow<List<WordEntity>> {
        return wordsDao.getAllWords()
    }

    override fun getRandomWord(): Flow<WordEntity> {
        return wordsDao.getRandomWord()
    }

    override fun checkIfExist(word: String): Boolean {
        return wordsDao.checkIfExist(word)
    }
}