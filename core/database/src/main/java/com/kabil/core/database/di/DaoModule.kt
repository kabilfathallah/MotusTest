package com.kabil.core.database.di

import com.kabil.core.database.AppDatabase
import com.kabil.core.database.dao.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesWordsDao(
        database: AppDatabase,
    ): WordsDao = database.wordsDao()
}