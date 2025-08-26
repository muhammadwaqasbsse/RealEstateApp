package com.waqas.realestateapptask.data.remote

data class ListingResponse(
    val items: List<ListingDetail>,
    val totalCount: Int
)

data class ListingDetail(
    val id: Int,
    val bedrooms: Int? = null,
    val city: String,
    val area: Int,
    val url: String? = null,
    val price: Int,
    val professional: String,
    val propertyType: String,
    val offerType: Int,
    val rooms: Int? = null
)
