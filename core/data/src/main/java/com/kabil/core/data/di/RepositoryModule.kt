package com.kabil.core.data.di

import com.kabil.core.data.WordsRepositoryImpl
import com.kabil.core.database.dao.WordsDao
import com.kabil.core.database.datasource.LocalDatasource
import com.kabil.core.domain.repository.WordsRepository
import com.kabil.core.network.datasource.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesWordsRepository(dataSource: RemoteDataSource, localDatasource: LocalDatasource): WordsRepository {
        return WordsRepositoryImpl(dataSource, localDatasource)
    }


}