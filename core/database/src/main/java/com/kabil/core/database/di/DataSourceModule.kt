package com.kabil.core.database.di

import com.kabil.core.database.datasource.LocalDatasource
import com.kabil.core.database.datasource.LocalDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    fun bindsLocalDatasource(dataSource: LocalDatasourceImpl): LocalDatasource
}