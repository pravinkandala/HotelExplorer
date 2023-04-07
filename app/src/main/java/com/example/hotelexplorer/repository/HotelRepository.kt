package com.example.hotelexplorer.repository

import com.example.hotelexplorer.network.HotelSearchApi

interface HotelRepository {
    suspend fun getFirstGiaid(location: String): String
}

class HotelRepositoryImpl(private val hotelSearchApi: HotelSearchApi) : HotelRepository {

    override suspend fun getFirstGiaid(location: String): String {
        val response = hotelSearchApi.searchRegions(location)
        return response.sr.firstOrNull()?.gaiaId ?: ""
    }
}