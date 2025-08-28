package com.waqas.immobilien.domain.usecase

import com.waqas.immobilien.domain.model.Property
import com.waqas.immobilien.domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPropertiesUseCaseTest {
    private val repo: PropertyRepository = mockk()
    private val useCase = GetPropertiesUseCase(repo)

    @Test
    fun `invoke should return property list`() = runTest {
        val fakeList = listOf(Property(1, 2, "Berlin", 100, null, 1000, "Agent", "House", 1, 4))
        coEvery { repo.fetchProperties() } returns fakeList

        val result = useCase()

        assertEquals(fakeList, result)
    }
}
