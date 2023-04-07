package com.example.hotelexplorer.network

import com.example.hotelexplorer.data.model.HotelListRequest
import com.example.hotelexplorer.data.model.HotelListResponse
import com.example.hotelexplorer.data.model.SearchResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HotelSearchApi {

    @GET("locations/v3/search")
    suspend fun searchRegions(
        @Query("q") destination: String
    ): SearchResult

    @POST("properties/v2/list")
    suspend fun hotelList(
        @Body request: HotelListRequest
    ): HotelListResponse
}
