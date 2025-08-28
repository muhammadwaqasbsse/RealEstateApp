package com.android.immobilien.presentation.viewmodel.property

import com.android.immobilien.domain.model.Property

/**
 * Represents the current state of the property listing screen.
 *
 * @param isLoading Indicates if data is currently being fetched
 * @param properties The list of properties to display (empty during loading or error states)
 * @param error Message describing any error that occurred during data fetching
 */
data class PropertyListState(
    val isLoading: Boolean = false,
    val properties: List<Property> = emptyList(),
    val error: String? = null,
)

/**
 * Events that can be triggered from the UI to interact with the property list.
 * These events typically correspond to user actions or system events.
 */
sealed class PropertyListEvent {
    /**
     * Event indicating that the property list should be loaded or refreshed.
     * This is typically triggered by the user clicking on retry or on initial screen load.
     */
    data object LoadingPropertyList : PropertyListEvent()
}
