package com.android.immobilien.presentation.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.android.immobilien.domain.model.Property
import com.android.immobilien.presentation.viewmodel.property.PropertyListEvent
import com.android.immobilien.presentation.viewmodel.property.PropertyListState
import com.android.immobilien.presentation.viewmodel.property.PropertyViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class PropertyScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createMockViewModel(state: PropertyListState): PropertyViewModel {
        val mockViewModel: PropertyViewModel = mockk(relaxed = true)
        val stateFlow = MutableStateFlow(state)

        every { mockViewModel.state } returns stateFlow
        every { mockViewModel.effect } returns MutableSharedFlow()
        every { mockViewModel.onEvent(any()) } answers {}

        return mockViewModel
    }

    @Test
    fun whenLoadingState_showLoadingIndicator() {
        val mockViewModel = createMockViewModel(PropertyListState(isLoading = true))

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = {},
            )
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun whenErrorState_showErrorMessageAndRetryButton() {
        val errorMessage = "Test error message"
        val mockViewModel = createMockViewModel(PropertyListState(error = errorMessage))

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = {},
            )
        }

        composeTestRule.onNodeWithTag("message_text").assertIsDisplayed()
//        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
        composeTestRule.onNodeWithTag("retry_button").assertIsDisplayed()
    }

    @Test
    fun emptyState_showsEmptyMessage() {
        val mockViewModel = createMockViewModel(PropertyListState(isLoading = true))

        val stateFlow =
            MutableStateFlow(
                PropertyListState(properties = emptyList()),
            )

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = { },
            )
        }

        composeTestRule
            .onNodeWithTag("message_text")
            .isDisplayed()
    }

    @Test
    fun whenSuccessState_showPropertyList() {
        val properties =
            listOf(
                Property(
                    id = 1,
                    bedrooms = 2,
                    city = "Berlin",
                    area = 120,
                    url = "test-url",
                    price = 100000,
                    professional = "Test Agency",
                    propertyType = "Apartment",
                    offerType = 1,
                    rooms = 3,
                ),
            )
        val mockViewModel = createMockViewModel(PropertyListState(properties = properties))

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = {},
            )
        }

        // Wait for the UI to update
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Berlin").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Berlin").assertIsDisplayed()
        // composeTestRule.onNodeWithText("Apartment").assertIsDisplayed()
        composeTestRule.onNodeWithText("Test Agency").assertIsDisplayed()
    }

    @Test
    fun whenSearchQueryEntered_filterProperties() {
        val properties =
            listOf(
                Property(
                    id = 1,
                    city = "Berlin",
                    propertyType = "Apartment",
                    price = 500000,
                    area = 120,
                    rooms = 3,
                    url = "https://example.com/image.jpg",
                    professional = "Agent 1",
                    offerType = 2,
                    bedrooms = 2,
                ),
                Property(
                    id = 2,
                    city = "Munich",
                    propertyType = "House",
                    price = 500000,
                    area = 120,
                    rooms = 3,
                    url = "https://example.com/image.jpg",
                    professional = "Agent 2",
                    offerType = 2,
                    bedrooms = 2,
                ),
            )
        val mockViewModel = createMockViewModel(PropertyListState(properties = properties))

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = {},
            )
        }

        // Verify all properties are initially shown
        composeTestRule.onNodeWithText("Berlin").assertExists()
        composeTestRule.onNodeWithText("Munich").assertExists()

        // Find the search field by test tag
        composeTestRule.onNodeWithTag("search_field").performTextInput("ber")

        composeTestRule.onNodeWithText("Berlin").assertIsDisplayed()
        composeTestRule.onNodeWithText("Munich").assertDoesNotExist()
    }

    @Test
    fun whenRetryButtonClicked_triggerRetryAction() {
        val mockViewModel = createMockViewModel(PropertyListState(error = "Test error"))

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = {},
            )
        }

        composeTestRule.onNodeWithTag("retry_button").performClick()

        verify { mockViewModel.onEvent(PropertyListEvent.LoadingPropertyList) }
    }

    @Test
    fun whenPropertyItemClicked_triggerNavigation() {
        val properties =
            listOf(
                Property(
                    id = 1,
                    city = "Berlin",
                    propertyType = "Apartment",
                    price = 500000,
                    area = 120,
                    rooms = 3,
                    url = "https://example.com/image.jpg",
                    professional = "Test Agent",
                    offerType = 2,
                    bedrooms = 2,
                ),
            )
        val mockViewModel = createMockViewModel(PropertyListState(properties = properties))
        var navigationTriggered = false

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = { id ->
                    navigationTriggered = true
                    assert(id == 1)
                },
            )
        }

        // Wait for the UI to update
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithText("Berlin").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithText("Berlin").performClick()
        assert(navigationTriggered)
    }
}
