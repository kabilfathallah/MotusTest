package com.kabil.core.network.datasource

import com.kabil.core.network.ApiClient
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiClient: ApiClient
) : RemoteDataSource {
    override suspend fun getWords(): List<String> {
       return apiClient.getWords().split("\n")
    }
}