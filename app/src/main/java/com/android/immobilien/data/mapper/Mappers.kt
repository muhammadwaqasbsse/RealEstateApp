package com.android.immobilien.data.mapper

import com.android.immobilien.data.remote.PropertyDetail
import com.android.immobilien.domain.model.Property

/**
 * Converts API response data into the domain model used throughout the app.
 * This mapping function isolates the data layer specifics from the business logic.
 */
fun PropertyDetail.toDomain() =
    Property(
        id = id,
        bedrooms = bedrooms,
        city = city,
        area = area,
        url = url,
        price = price,
        professional = professional,
        propertyType = propertyType,
        offerType = offerType,
        rooms = rooms,
    )
