package com.waqas.realestateapptask.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ListingApi {
    @GET("Listings.json")
    suspend fun getListings(): ListingResponse

    @GET("Listings/{id}.json")
    suspend fun getListingDetail(@Path("id") id: Int): ListingDetail
}