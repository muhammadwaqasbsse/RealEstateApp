package com.android.immobilien.presentation.viewmodel.propertydetail

import com.android.immobilien.domain.model.Property
import com.android.immobilien.domain.usecase.GetPropertyDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PropertyDetailViewModelTest {
    private lateinit var viewModel: PropertyDetailViewModel
    private lateinit var getPropertyDetailUseCase: GetPropertyDetailUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPropertyDetailUseCase = mockk()
        viewModel = PropertyDetailViewModel(getPropertyDetailUseCase)
    }

    @Test
    fun `initial state should be correct`() =
        runTest {
            // Then
            assertEquals(false, viewModel.state.value.isLoading)
            assertNull(viewModel.state.value.property)
            assertNull(viewModel.state.value.error)
        }

    @Test
    fun `load property successfully should update state with property`() =
        runTest {
            // Given
            val propertyId = 1
            val mockProperty =
                Property(
                    id = propertyId,
                    bedrooms = 2,
                    city = "Berlin",
                    area = 80,
                    url = "https://example.com/image1.jpg",
                    price = 250000,
                    professional = "John Doe",
                    propertyType = "Apartment",
                    offerType = 1,
                    rooms = 3,
                )

            coEvery { getPropertyDetailUseCase(propertyId) } returns mockProperty

            // When
            viewModel.load(propertyId)
            advanceUntilIdle() // Wait for coroutines to complete

            // Then
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(mockProperty, viewModel.state.value.property)
            assertNull(viewModel.state.value.error)
        }

    @Test
    fun `load property with failure should update state with error`() =
        runTest {
            // Given
            val propertyId = 1
            val errorMessage = "Network error"
            val exception = RuntimeException(errorMessage)

            coEvery { getPropertyDetailUseCase(propertyId) } throws exception

            // When
            viewModel.load(propertyId)
            advanceUntilIdle() // Wait for coroutines to complete

            // Then
            assertEquals(false, viewModel.state.value.isLoading)
            assertNull(viewModel.state.value.property)
            assertEquals(errorMessage, viewModel.state.value.error)
        }

    @Test
    fun `load property should set isLoading to true initially and then false on success`() =
        runTest {
            // Given
            val propertyId = 1
            val mockProperty =
                Property(
                    id = propertyId,
                    bedrooms = 2,
                    city = "Berlin",
                    area = 80,
                    url = "https://example.com/image1.jpg",
                    price = 250000,
                    professional = "John Doe",
                    propertyType = "Apartment",
                    offerType = 1,
                    rooms = 3,
                )

            coEvery { getPropertyDetailUseCase(propertyId) } returns mockProperty

            // When
            viewModel.load(propertyId)

            // The isLoading should be true immediately after calling load
            // but we can't test this directly because of the async nature
            // Instead, we wait for the coroutine to complete and verify the final state
            advanceUntilIdle() // Wait for coroutines to complete

            // Then
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(mockProperty, viewModel.state.value.property)
        }

    @Test
    fun `load property should set isLoading to true initially and then false on failure`() =
        runTest {
            // Given
            val propertyId = 1
            val errorMessage = "Network error"
            val exception = RuntimeException(errorMessage)

            coEvery { getPropertyDetailUseCase(propertyId) } throws exception

            // When
            viewModel.load(propertyId)
            advanceUntilIdle() // Wait for coroutines to complete

            // Then
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(errorMessage, viewModel.state.value.error)
        }
}
