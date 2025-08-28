package com.android.immobilien.presentation.viewmodel.property

import com.android.immobilien.domain.model.Property
import com.android.immobilien.domain.usecase.GetPropertiesUseCase
import com.android.immobilien.presentation.viewmodel.common.Effect
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PropertyViewModelTest {
    private lateinit var viewModel: PropertyViewModel
    private lateinit var getPropertiesUseCase: GetPropertiesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getPropertiesUseCase = mockk()
        viewModel = PropertyViewModel(getPropertiesUseCase)
    }

    @Test
    fun `initial state should be empty`() =
        runTest {
            // Then
            assertEquals(PropertyListState(), viewModel.state.value)
        }

    @Test
    fun `load properties successfully should update state and show success toast`() =
        runTest {
            // Given
            val mockProperties =
                listOf(
                    Property(
                        id = 1,
                        bedrooms = 2,
                        city = "Berlin",
                        area = 80,
                        url = "https://example.com/image1.jpg",
                        price = 250000,
                        professional = "John Doe",
                        propertyType = "Apartment",
                        offerType = 1,
                        rooms = 3,
                    ),
                    Property(
                        id = 2,
                        bedrooms = 3,
                        city = "Munich",
                        area = 120,
                        url = "https://example.com/image2.jpg",
                        price = 450000,
                        professional = "Jane Smith",
                        propertyType = "House",
                        offerType = 1,
                        rooms = 4,
                    ),
                )

            coEvery { getPropertiesUseCase() } returns mockProperties

            // Collect effects in background
            val effects = mutableListOf<Effect>()
            val effectJob =
                launch {
                    viewModel.effect.toList(effects)
                }

            // When
            viewModel.onEvent(PropertyListEvent.LoadingPropertyList)
            advanceUntilIdle() // Wait for coroutines to complete

            // Then
            assertEquals(mockProperties, viewModel.state.value.properties)
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(null, viewModel.state.value.error)

            // Verify effect was emitted
            assertEquals(1, effects.size)
            assertEquals(Effect.ShowToast("Listings loaded!"), effects[0])

            // Verify use case was called
            coVerify { getPropertiesUseCase() }

            effectJob.cancel()
        }

    @Test
    fun `load properties with failure should update state and show error toast`() =
        runTest {
            // Given
            val errorMessage = "Network error"
            val exception = RuntimeException(errorMessage)

            coEvery { getPropertiesUseCase() } throws exception

            // Collect effects in background
            val effects = mutableListOf<Effect>()
            val effectJob =
                launch {
                    viewModel.effect.toList(effects)
                }

            // When
            viewModel.onEvent(PropertyListEvent.LoadingPropertyList)
            advanceUntilIdle() // Wait for coroutines to complete

            // Then
            assertEquals(emptyList<Property>(), viewModel.state.value.properties)
            assertEquals(false, viewModel.state.value.isLoading)
            assertEquals(errorMessage, viewModel.state.value.error)

            // Verify effect was emitted
            assertEquals(1, effects.size)
            assertEquals(Effect.ShowToast("Error loading listings"), effects[0])

            // Verify use case was called
            coVerify { getPropertiesUseCase() }

            effectJob.cancel()
        }

    @Test
    fun `load properties should set isLoading to false even on success`() =
        runTest {
            // Given
            val mockProperties =
                listOf(
                    Property(
                        id = 1,
                        bedrooms = 2,
                        city = "Berlin",
                        area = 80,
                        url = "https://example.com/image1.jpg",
                        price = 250000,
                        professional = "John Doe",
                        propertyType = "Apartment",
                        offerType = 1,
                        rooms = 3,
                    ),
                )

            coEvery { getPropertiesUseCase() } returns mockProperties

            // When
            viewModel.onEvent(PropertyListEvent.LoadingPropertyList)

            // Then
            assertEquals(false, viewModel.state.value.isLoading)
        }
}
