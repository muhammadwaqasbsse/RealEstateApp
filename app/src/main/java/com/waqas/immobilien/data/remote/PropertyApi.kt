package com.waqas.immobilien.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface PropertyApi {
    @GET("listings.json")
    suspend fun fetchProperties(): PropertyListResponse

    @GET("listings/{id}.json")
    suspend fun fetchPropertyDetail(@Path("id") id: Int): PropertyDetail
}