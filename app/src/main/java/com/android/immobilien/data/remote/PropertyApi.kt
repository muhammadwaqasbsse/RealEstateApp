package com.android.immobilien.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface PropertyApi {
    @GET("listings.json")
    suspend fun getProperties(): PropertiesResponse

    @GET("listings/{id}.json")
    suspend fun getPropertyDetail(
        @Path("id") id: Int,
    ): PropertyDetail
}
