package com.android.immobilien.domain.usecase

import com.android.immobilien.domain.model.Property
import com.android.immobilien.domain.repository.PropertyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPropertyDetailUseCaseTest {
    private val repo: PropertyRepository = mockk()
    private val useCase = GetPropertyDetailUseCase(repo)

    @Test
    fun `invoke should return property detail`() =
        runTest {
            val property = Property(1, 3, "Paris", 120, null, 5000, "Agent", "Villa", 1, 5)
            coEvery { repo.getPropertyDetail(1) } returns property

            val result = useCase(1)

            assertEquals(property, result)
        }
}
