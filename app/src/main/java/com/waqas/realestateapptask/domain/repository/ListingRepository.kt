package com.waqas.realestateapptask.domain.repository

import com.waqas.realestateapptask.domain.model.Listing

interface ListingRepository {
    suspend fun getListings(): List<Listing>
    suspend fun getListingDetail(id: Int): Listing
}