package com.example.hotelexplorer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelexplorer.data.model.HotelListRequest
import com.example.hotelexplorer.repository.HotelRepository
import java.time.LocalDate
import kotlinx.coroutines.launch

class HotelSearchViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

    var searchQuery by mutableStateOf("")

    fun searchLocation() {
        if (searchQuery.isNotBlank()) {
            viewModelScope.launch {
                try {
                    val firstGiaid = hotelRepository.getFirstGiaid(searchQuery)

                    val today = LocalDate.now()

                    val checkInDate = today
                    val checkOutDate = today.plusDays(7)

                    // Create the room object with children
                    val room = HotelListRequest.Room(
                        adults = 1
                    )

                    // Create the destination object
                    val destination = HotelListRequest.Destination(regionId = firstGiaid)

                    // Create the hotel list request object with the above parameters
                    val hotelListRequest = HotelListRequest(
                        currency = "USD",
                        locale = "en_US",
                        destination = destination,
                        checkInDate = HotelListRequest.ReservationDate(
                            day = checkInDate.dayOfMonth,
                            month = checkInDate.monthValue,
                            year = checkInDate.year
                        ),
                        checkOutDate = HotelListRequest.ReservationDate(
                            day = checkOutDate.dayOfMonth,
                            month = checkOutDate.monthValue,
                            year = checkOutDate.year
                        ),
                        rooms = listOf(room),
                        resultsStartingIndex = 0,
                        resultsSize = 10,
                        sort = "PRICE_LOW_TO_HIGH",
                    )

                    val hotelList = hotelRepository.getHotelList(hotelListRequest)
                } catch (e: Exception) {
                    Log.d("Pravin", "Exception while searching - ${e.message}")
                }
            }
        }
    }
}