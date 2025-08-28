package com.android.immobilien.domain.repository

import com.android.immobilien.domain.model.Property

/**
 * Defines the contract for accessing property data from various sources.
 * This abstraction allows the domain layer to remain independent of data source implementation details.
 */
interface PropertyRepository {
    /**
     * Fetches a list of available properties.
     * @return List of property
     */
    suspend fun getProperties(): List<Property>

    /**
     * Retrieves detailed information for a specific property.
     * @param id The unique identifier of the property to retrieve
     * @return Complete property details including all available fields
     */
    suspend fun getPropertyDetail(id: Int): Property
}
