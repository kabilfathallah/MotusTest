package com.kabil.core.data

import com.kabil.core.database.datasource.LocalDatasource
import com.kabil.core.domain.repository.WordsRepository
import com.kabil.core.network.datasource.RemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDatasource
) : WordsRepository {

    override suspend fun getWords(): List<String> {
        if (localDataSource.getAllWords().first().isEmpty()) {
            val remoteWords = remoteDataSource.getWords()
            localDataSource.insertAll(remoteWords)
        }
        return localDataSource.getAllWords().first().map { it.text }
    }

    override suspend fun getRandomWord(): String {
        return localDataSource.getRandomWord().first().text
    }

}