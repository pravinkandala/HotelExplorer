package com.example.hotelexplorer.data.model

data class SearchResult(
    val sr: List<RegionResult>
)

data class RegionResult(
    val gaiaId: String
)
