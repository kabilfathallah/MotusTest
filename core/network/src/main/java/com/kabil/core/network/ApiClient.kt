package com.kabil.core.network

import retrofit2.http.GET

interface ApiClient {
    @GET("a57b7e7793e27610a4ee97027221bf4e/raw/950eb7a7527f86482ce4dc531c43f63496786d16/gistfile1.txt")
    suspend fun getWords(): String

}