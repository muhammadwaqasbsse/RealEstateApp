package com.waqas.immobilien.data.repository

import com.waqas.immobilien.data.mapper.toDomain
import com.waqas.immobilien.data.remote.PropertyApi
import com.waqas.immobilien.domain.model.Property
import com.waqas.immobilien.domain.repository.PropertyRepository

class PropertyRepositoryImpl(
    private val propertyApi: PropertyApi,
) : PropertyRepository {
    override suspend fun fetchProperties(): List<Property> {
        val properties = propertyApi.fetchProperties()
        val propertiesList = properties.items
        return propertiesList.map { it.toDomain() }
    }

    override suspend fun fetchPropertyDetail(id: Int): Property {
        val propertyDetail = propertyApi.fetchPropertyDetail(id)
        return propertyDetail.toDomain()
    }
}
