package com.kabil.core.network.datasource

interface RemoteDataSource {
    suspend fun getWords(): List<String>
}