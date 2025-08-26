package com.waqas.realestateapptask.domain.model

data class Listing(
    val id: Int,
    val bedrooms: Int?,
    val city: String,
    val area: Int,
    val url: String?,
    val price: Int,
    val professional: String,
    val propertyType: String,
    val offerType: Int,
    val rooms: Int?
)
