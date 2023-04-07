package com.example.hotelexplorer.network

import com.example.hotelexplorer.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface HotelSearchApi {

    @GET("locations/v3/search")
    suspend fun searchRegions(
        @Query("q") destination: String,
    ): SearchResult
}