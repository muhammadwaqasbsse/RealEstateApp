package com.waqas.realestateapptask.data.repository

import com.waqas.realestateapptask.data.mapper.toDomain
import com.waqas.realestateapptask.data.remote.ListingApi
import com.waqas.realestateapptask.domain.model.Listing
import com.waqas.realestateapptask.domain.repository.ListingRepository

class ListingRepositoryImpl(
    private val api: ListingApi
): ListingRepository {
    override suspend fun getListings(): List<Listing> {
        val listings = api.getListings()
        val entities = listings.items
        return entities.map { it.toDomain() }
    }

    override suspend fun getListingDetail(id: Int): Listing {
        val listingDetail = api.getListingDetail(id)
        return listingDetail.toDomain()
    }
}