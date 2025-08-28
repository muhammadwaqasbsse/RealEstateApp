package com.android.immobilien.data.remote

/**
 * Wrapper for the API response containing a list of properties.
 */
data class PropertiesResponse(
    val items: List<PropertyDetail>,
    val totalCount: Int,
)

/**
 * Detailed information about a single property as returned by the API.
 * Note: Some fields are nullable where the API might not always provide values.
 */
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
    val rooms: Int? = null,
)
