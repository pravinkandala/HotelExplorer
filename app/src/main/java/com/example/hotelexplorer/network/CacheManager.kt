package com.example.hotelexplorer.network

import android.content.Context
import com.example.hotelexplorer.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File

class CacheManager(private val context: Context) {
    companion object {
        private const val CACHE_SIZE = 10 * 1024 * 1024 // 10 MB
        private const val CACHE_MAX_AGE = 60 // 1 minute
    }

    val cache: Cache by lazy {
        val cacheDirectory = File(context.cacheDir, "http-cache")
        Cache(cacheDirectory, CACHE_SIZE.toLong())
    }

    fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("X-RapidAPI-Host", BuildConfig.API_HOST)
                    .header("X-RapidAPI-Key", BuildConfig.API_KEY)
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                val response = chain.proceed(request)
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$CACHE_MAX_AGE")
                    .build()
            }
            .build()
    }
}
