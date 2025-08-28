package com.android.immobilien.presentation.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.immobilien.domain.model.Property
import com.android.immobilien.presentation.viewmodel.propertydetail.PropertyDetailState
import com.android.immobilien.presentation.viewmodel.propertydetail.PropertyDetailViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: PropertyDetailViewModel
    private lateinit var stateFlow: MutableStateFlow<PropertyDetailState>
    private val mockProperty =
        Property(
            id = 1,
            city = "Berlin",
            propertyType = "Apartment",
            price = 500000,
            area = 120,
            rooms = 3,
            url = "https://example.com/image.jpg",
            professional = "",
            offerType = 2,
            bedrooms = 2,
        )

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        stateFlow = MutableStateFlow(PropertyDetailState())
        every { mockViewModel.state } returns stateFlow
    }

    @Test
    fun loadingState_showsProgressIndicator() {
        stateFlow.value = PropertyDetailState(isLoading = true)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorText() {
        val errorMessage = "Network error"
        stateFlow.value = PropertyDetailState(error = errorMessage)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        // Verify error message is displayed
        assertTextDisplayed("Error: $errorMessage")
    }

    @Test
    fun successState_showsPropertyDetails() {
        stateFlow.value = PropertyDetailState(property = mockProperty)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        // Verify property details are displayed
        assertTextDisplayed(mockProperty.city)

        assertTextDisplayed("${mockProperty.propertyType} for sale")

        assertTextDisplayed("Financing from €1,040 /month")

        assertTextDisplayed(mockProperty.rooms.toString())

        assertTextDisplayed("Immediately")
    }

    @Test
    fun backButton_callsOnBackClick() {
        var backClicked = false
        stateFlow.value = PropertyDetailState(property = mockProperty)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = { backClicked = true },
                viewModel = mockViewModel,
            )
        }

        // Click the back button
        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()

        // Verify the callback was called
        assert(backClicked)
    }

    @Test
    fun callButton_isDisplayedAndClickable() {
        stateFlow.value = PropertyDetailState(property = mockProperty)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        composeTestRule
            .onNodeWithText("Call")
            .assertExists()
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun price_isFormattedCorrectly() {
        stateFlow.value = PropertyDetailState(property = mockProperty)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        assertTextDisplayed(mockProperty.city)
        composeTestRule
            .onNodeWithText("${mockProperty.price} €")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun stateTransitions_fromLoadingToSuccess() {
        stateFlow.value = PropertyDetailState(isLoading = true)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        // Wait for UI to update
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertExists()
            .assertIsDisplayed()

        // Transition to success state
        stateFlow.value = PropertyDetailState(property = mockProperty)

        // Wait for UI to update again
        composeTestRule.waitForIdle()

        composeTestRule
            .onNodeWithText(mockProperty.city)
            .assertExists()
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertDoesNotExist()
    }

    @Test
    fun stateTransitions_fromSuccessToError() {
        // Start with success state
        stateFlow.value = PropertyDetailState(property = mockProperty)

        composeTestRule.setContent {
            PropertyDetailScreen(
                id = 1,
                onBackClick = {},
                viewModel = mockViewModel,
            )
        }

        composeTestRule
            .onNodeWithText(mockProperty.city)
            .assertExists()
            .assertIsDisplayed()

        val errorMessage = "Network error"
        stateFlow.value = PropertyDetailState(error = errorMessage)

        assertTextDisplayed("Error: $errorMessage")

        assertTextDoesNotExist(mockProperty.city)
    }

    private fun assertTextDisplayed(text: String) {
        composeTestRule.onNodeWithText(text).assertExists().assertIsDisplayed()
    }

    private fun assertTextDoesNotExist(text: String) {
        composeTestRule.onNodeWithText(text).assertDoesNotExist()
    }
}
