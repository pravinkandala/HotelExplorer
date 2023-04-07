package com.example.hotelexplorer.di

import com.example.hotelexplorer.network.RetrofitClient
import com.example.hotelexplorer.repository.HotelRepository
import com.example.hotelexplorer.repository.HotelRepositoryImpl
import com.example.hotelexplorer.ui.viewmodel.HotelSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient.hotelSearchApi }

    single<HotelRepository> {
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