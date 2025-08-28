package com.android.immobilien.data.repository

import com.android.immobilien.data.mapper.toDomain
import com.android.immobilien.data.remote.PropertiesResponse
import com.android.immobilien.data.remote.PropertyApi
import com.android.immobilien.data.remote.PropertyDetail
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
    fun `getProperties should return mapped list`() =
        runTest {
            val dto = PropertyDetail(1, 2, "Berlin", 120, null, 100000, "Agent", "House", 1, 4)
            coEvery { api.getProperties() } returns PropertiesResponse(listOf(dto), 1)

            val result = repository.getProperties()

            assertEquals(1, result.size)
            assertEquals(dto.toDomain(), result.first())
        }

    @Test
    fun `getPropertyDetail should return mapped property`() =
        runTest {
            val dto = PropertyDetail(2, 3, "Paris", 90, null, 200000, "AgentX", "Flat", 2, 5)
            coEvery { api.getPropertyDetail(2) } returns dto

            val result = repository.getPropertyDetail(2)

            assertEquals(dto.toDomain(), result)
        }
}
