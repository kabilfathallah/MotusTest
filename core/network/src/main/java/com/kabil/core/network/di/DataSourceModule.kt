package com.kabil.core.network.di

import com.kabil.core.network.datasource.RemoteDataSource
import com.kabil.core.network.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {

    @Binds
    fun bindsRemoteDatasource(dataSource: RemoteDataSourceImpl): RemoteDataSource
}