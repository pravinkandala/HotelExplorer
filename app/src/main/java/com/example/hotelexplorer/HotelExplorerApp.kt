package com.example.hotelexplorer

import android.app.Application
import com.example.hotelexplorer.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HotelExplorerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HotelExplorerApp)
            modules(appModule)
        }
    }
}