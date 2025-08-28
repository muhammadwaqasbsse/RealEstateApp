package com.android.immobilien.data.repository

import com.android.immobilien.data.mapper.toDomain
import com.android.immobilien.data.remote.PropertyApi
import com.android.immobilien.domain.model.Property
import com.android.immobilien.domain.repository.PropertyRepository

class PropertyRepositoryImpl(
    private val propertyApi: PropertyApi,
) : PropertyRepository {
    override suspend fun getProperties(): List<Property> {
        val properties = propertyApi.getProperties()
        val propertiesList = properties.items
        return propertiesList.map { it.toDomain() }
    }

    override suspend fun getPropertyDetail(id: Int): Property {
        val propertyDetail = propertyApi.getPropertyDetail(id)
        return propertyDetail.toDomain()
    }
}
