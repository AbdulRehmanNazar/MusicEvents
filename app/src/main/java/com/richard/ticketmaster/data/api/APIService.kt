package com.richard.ticketmaster.data.api

import com.richard.ticketmaster.data.models.TicketMasterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Remote API end points
 */
interface APIService {
    @GET("events.json")
    suspend fun getTickerMasterEvents(
        @Query("apikey") apiKey: String,
        @Query("keyword") searchText: String = "",
        @Query("page") page: String = "0",
        ): Response<TicketMasterResponse>
}