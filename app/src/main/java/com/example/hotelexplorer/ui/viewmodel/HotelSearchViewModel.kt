package com.example.hotelexplorer.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hotelexplorer.data.model.HotelListRequest
import com.example.hotelexplorer.data.model.HotelListResponse.PropertySearch.Property
import com.example.hotelexplorer.repository.HotelRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class HotelSearchViewModel(private val hotelRepository: HotelRepository) : ViewModel() {

    var searchQuery by mutableStateOf("")

    private val _hotels = MutableLiveData<List<Property>>()
    val hotels: LiveData<List<Property>> = _hotels

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchLocation() {
        if (searchQuery.isBlank()) {
            return
        }
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val destinationId = getDestinationId(searchQuery)
                val hotelListRequest = createHotelListRequest(destinationId)
                val hotelList = hotelRepository.getHotelList(hotelListRequest)
                _hotels.postValue(hotelList.data.propertySearch.properties)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.d("HotelSearchViewModel", "Exception while searching - ${e.message}")
            }
        }
    }

    private suspend fun getDestinationId(searchQuery: String): String {
        val firstGiaid = hotelRepository.getFirstGiaid(searchQuery)
        return HotelListRequest.Destination(regionId = firstGiaid).regionId
    }

    private fun createHotelListRequest(destinationId: String): HotelListRequest {
        val today = LocalDate.now()
        val checkOutDate = today.plusDays(7)
        val room = HotelListRequest.Room(adults = 1)

        return HotelListRequest(
            currency = "USD",
            locale = "en_US",
            destination = HotelListRequest.Destination(regionId = destinationId),
            checkInDate = HotelListRequest.ReservationDate(
                day = today.dayOfMonth,
                month = today.monthValue,
                year = today.year
            ),
            checkOutDate = HotelListRequest.ReservationDate(
                day = checkOutDate.dayOfMonth,
                month = checkOutDate.monthValue,
                year = checkOutDate.year
            ),
            rooms = listOf(room),
            resultsStartingIndex = 0,
            resultsSize = 10,
            sort = "PRICE_LOW_TO_HIGH"
        )
    }
}
