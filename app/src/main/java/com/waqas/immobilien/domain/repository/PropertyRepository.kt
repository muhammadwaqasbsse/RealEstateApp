package com.waqas.immobilien.domain.repository

import com.waqas.immobilien.domain.model.Property

interface PropertyRepository {
    suspend fun fetchProperties(): List<Property>

    suspend fun fetchPropertyDetail(id: Int): Property
}
