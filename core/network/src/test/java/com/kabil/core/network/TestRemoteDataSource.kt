package com.kabil.core.network

import com.kabil.core.network.datasource.RemoteDataSourceImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class TestRemoteDataSource {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiClient

    private lateinit var remoteDataSource: RemoteDataSourceImpl

    @Before //Using JUnit5
    fun setup() {
        server = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(ApiClient::class.java)

        remoteDataSource = RemoteDataSourceImpl(api)
    }

    @Test
    fun testResponse() = runTest {
        val response = MockResponse().setBody("HELLO\nWORLD\nANDROID")
        server.enqueue(response)
        val data = remoteDataSource.getWords()
        server.takeRequest()
        assertEquals(data, listOf("HELLO", "WORLD", "ANDROID"))
    }

    @After
    fun destroy() {
        server.shutdown()
    }

}