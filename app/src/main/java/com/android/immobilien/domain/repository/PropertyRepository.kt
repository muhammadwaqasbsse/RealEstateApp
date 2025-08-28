package com.android.immobilien.domain.repository

import com.android.immobilien.domain.model.Property

interface PropertyRepository {
    suspend fun getProperties(): List<Property>

    suspend fun getPropertyDetail(id: Int): Property
}
