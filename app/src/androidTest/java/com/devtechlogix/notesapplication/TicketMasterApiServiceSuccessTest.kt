package com.devtechlogix.notesapplication

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import com.richard.ticketmaster.data.api.APIService
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.junit.Assert.*


@ExperimentalCoroutinesApi
class TicketMasterApiServiceSuccessTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: APIService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("https://app.ticketmaster.com/discovery/v2/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testGetEvents(): Unit = runBlocking {
        val apiKey = "iqSRdVEjWINZ4D3Gz8fBs6VD3c4gCEGw"
        apiService.getTickerMasterEvents(apiKey)
        val eventResponse = apiService.getTickerMasterEvents(apiKey)
        assertEquals(eventResponse.isSuccessful, true)
    }
}