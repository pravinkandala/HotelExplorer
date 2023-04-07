package com.example.hotelexplorer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelexplorer.repository.HotelRepository
import kotlinx.coroutines.launch

class HotelSearchViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

    var searchQuery by mutableStateOf("")

    fun searchLocation() {
        if (searchQuery.isNotBlank()) {
            viewModelScope.launch {
                try {
                    Log.d("Pravin", hotelRepository.getFirstGiaid(searchQuery))
                } catch (e: Exception) {
                    Log.d("Pravin", "Exception while searching - ${e.message}")
                }
            }
        }
    }
}