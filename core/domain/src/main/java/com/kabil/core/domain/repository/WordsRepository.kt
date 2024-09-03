package com.kabil.core.domain.repository

interface WordsRepository {
    suspend fun getWords(): List<String>
    suspend fun getRandomWord(): String
}