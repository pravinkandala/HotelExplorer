package com.example.hotelexplorer.di

import com.example.hotelexplorer.network.CacheManager
import com.example.hotelexplorer.network.RetrofitClient
import com.example.hotelexplorer.repository.HotelRepository
import com.example.hotelexplorer.repository.HotelRepositoryImpl
import com.example.hotelexplorer.ui.viewmodel.HotelSearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { CacheManager(androidContext()) }

    single { RetrofitClient.createHotelSearchApi(get()) }

    factory<HotelRepository> {
        HotelRepositoryImpl(
            hotelSearchApi = get()
        )
    }

    viewModel {
        HotelSearchViewModel(
            hotelRepository = get()
        )
    }
}
