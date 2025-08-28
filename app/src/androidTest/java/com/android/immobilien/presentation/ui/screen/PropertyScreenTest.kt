import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.immobilien.presentation.ui.screen.PropertyScreen
import com.android.immobilien.presentation.viewmodel.common.Effect
import com.android.immobilien.presentation.viewmodel.property.PropertyListEvent
import com.android.immobilien.presentation.viewmodel.property.PropertyListState
import com.android.immobilien.presentation.viewmodel.property.PropertyListViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: PropertyListViewModel
    private val mockOnItemClick: (Int) -> Unit = mockk(relaxed = true)
    private lateinit var stateFlow: MutableStateFlow<PropertyListState>
    private lateinit var effectFlow: SharedFlow<Effect>

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)
        stateFlow = MutableStateFlow(PropertyListState())
        effectFlow = MutableSharedFlow()

        // Mock the state and effect flows
        every { mockViewModel.state } returns stateFlow
        every { mockViewModel.effect } returns effectFlow
    }

    @Test
    fun loadingState_showsProgressIndicator() {
        stateFlow.value = PropertyListState(isLoading = true)

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = mockOnItemClick,
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertExists()
    }

    @Test
    fun errorState_showsErrorMessageAndRetryButton() {
        stateFlow.value = PropertyListState(error = "Network error")

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = mockOnItemClick,
            )
        }

        composeTestRule
            .onNodeWithText("Error: Network error")
            .assertExists()

        composeTestRule
            .onNodeWithText("Retry")
            .performClick()

        verify { mockViewModel.onEvent(PropertyListEvent.LoadingPropertyList) }
    }

    @Test
    fun emptyState_showsEmptyMessage() {
        stateFlow.value = PropertyListState(properties = emptyList())

        composeTestRule.setContent {
            PropertyScreen(
                viewModel = mockViewModel,
                onItemClick = mockOnItemClick,
            )
        }

        composeTestRule
            .onNodeWithText("No properties found")
            .assertExists()
    }
}
