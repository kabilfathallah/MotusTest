package com.kabil.core.database.datasource

import com.kabil.core.database.model.WordEntity
import kotlinx.coroutines.flow.Flow

interface LocalDatasource {

    suspend fun insertAll(words: List<String>)

    fun getAllWords(): Flow<List<WordEntity>>

    fun getRandomWord(): Flow<WordEntity>

    fun checkIfExist(word: String): Boolean
}