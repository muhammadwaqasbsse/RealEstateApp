package com.waqas.immobilien.data.mapper

import com.waqas.immobilien.data.remote.PropertyDetail
import com.waqas.immobilien.domain.model.Property

fun PropertyDetail.toDomain() = Property(
    id, bedrooms, city, area, url, price, professional, propertyType, offerType, rooms
)