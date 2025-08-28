package com.waqas.immobilien.data.repository

import com.waqas.immobilien.data.mapper.toDomain
import com.waqas.immobilien.data.remote.PropertyApi
import com.waqas.immobilien.data.remote.PropertyDetail
import com.waqas.immobilien.data.remote.PropertyListResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PropertyRepositoryImplTest {

    private lateinit var repository: PropertyRepositoryImpl
    private val api: PropertyApi = mockk()

    @Before
    fun setup() {
        repository = PropertyRepositoryImpl(api)
    }

    @Test
    fun `fetchProperties should return mapped list`() = runTest {
        val dto = PropertyDetail(1, 2, "Berlin", 120, null, 100000, "Agent", "House", 1, 4)
        coEvery { api.fetchProperties() } returns PropertyListResponse(listOf(dto), 1)

        val result = repository.fetchProperties()

        assertEquals(1, result.size)
        assertEquals(dto.toDomain(), result.first())
    }

    @Test
    fun `fetchPropertyDetail should return mapped property`() = runTest {
        val dto = PropertyDetail(2, 3, "Paris", 90, null, 200000, "AgentX", "Flat", 2, 5)
        coEvery { api.fetchPropertyDetail(2) } returns dto

        val result = repository.fetchPropertyDetail(2)

        assertEquals(dto.toDomain(), result)
    }
}
