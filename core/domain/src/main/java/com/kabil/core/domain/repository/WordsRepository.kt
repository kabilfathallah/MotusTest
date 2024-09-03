package com.kabil.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface WordsRepository {
    suspend fun getWords(): Result<List<String>>
    fun getRandomWord(): Flow<String>
}