package com.waqas.immobilien.data.remote

data class PropertyListResponse(
    val items: List<PropertyDetail>,
    val totalCount: Int
)

data class PropertyDetail(
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
