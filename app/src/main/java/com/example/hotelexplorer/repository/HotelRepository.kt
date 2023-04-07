package com.example.hotelexplorer.repository

import com.example.hotelexplorer.data.model.HotelListRequest
import com.example.hotelexplorer.data.model.HotelListResponse
import com.example.hotelexplorer.network.HotelSearchApi

interface HotelRepository {
    suspend fun getFirstGiaid(location: String): String
    suspend fun getHotelList(request: HotelListRequest): HotelListResponse
}

class HotelRepositoryImpl(private val hotelSearchApi: HotelSearchApi) : HotelRepository {

    override suspend fun getFirstGiaid(location: String): String {
        val response = hotelSearchApi.searchRegions(location)
        return response.sr.firstOrNull()?.gaiaId ?: ""
    }

    override suspend fun getHotelList(request: HotelListRequest): HotelListResponse {
        return hotelSearchApi.hotelList(request)
    }
}