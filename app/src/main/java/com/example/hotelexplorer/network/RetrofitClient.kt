package com.example.hotelexplorer.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://hotels4.p.rapidapi.com"

    private val gson = GsonBuilder().create()

    fun createHotelSearchApi(cacheManager: CacheManager): HotelSearchApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(cacheManager.createOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(HotelSearchApi::class.java)
    }
}
