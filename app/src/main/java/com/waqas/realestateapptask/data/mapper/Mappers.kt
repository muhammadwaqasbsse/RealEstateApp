package com.waqas.realestateapptask.data.mapper

import com.waqas.realestateapptask.data.remote.ListingDetail
import com.waqas.realestateapptask.domain.model.Listing

fun ListingDetail.toDomain() = Listing(
    id, bedrooms, city, area, url, price, professional, propertyType, offerType, rooms
)