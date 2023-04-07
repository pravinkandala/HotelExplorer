package com.example.hotelexplorer.data.model

data class SearchResult(
    val sr: List<RegionResult>
) {
    data class RegionResult(
        val gaiaId: String
    )
}

data class HotelListRequest(
    val currency: String = "USD",
    val locale: String = "en_US",
    val destination: Destination,
    val checkInDate: ReservationDate,
    val checkOutDate: ReservationDate,
    val rooms: List<Room>,
    val resultsStartingIndex: Int,
    val resultsSize: Int,
    val sort: String
) {
    data class Destination(
        val regionId: String
    )

    data class ReservationDate(
        val day: Int,
        val month: Int,
        val year: Int
    )

    data class Room(
        val adults: Int,
        val children: List<Child>? = null
    ) {
        data class Child(
            val age: Int
        )
    }
}

data class HotelListResponse(
    val data: Data
) {
    data class Data(
        val propertySearch: PropertySearch
    )

    data class PropertySearch(
        val properties: List<Property>
    ) {
        data class Property(
            val id: String? = null,
            val name: String? = null,
            val availability: Availability? = null,
            val propertyImage: PropertyImage? = null,
            val offerBadge: OfferBadge? = null,
            val price: PropertyPrice? = null,
            val reviews: Reviews? = null
        )

        data class Availability(
            val available: Boolean
        )

        data class PropertyImage(
            val image: Image
        )

        data class Image(
            val url: String
        )

        data class OfferBadge(
            val primary: Ary,
            val secondary: Ary? = null
        ) {
            data class Ary(
                val text: String
            )
        }

        data class PropertyPrice(
            val displayMessages: List<DisplayMessage>,
            val priceMessages: List<PriceMessage>
        ) {
            data class DisplayMessage(
                val lineItems: List<LineItem>
            ) {
                data class LineItem(
                    val price: LineItemPrice? = null
                ) {
                    data class LineItemPrice(
                        val formatted: String
                    )
                }
            }

            data class PriceMessage(
                val value: String
            )
        }

        data class Reviews(
            val score: Double,
            val total: Long
        )
    }
}
