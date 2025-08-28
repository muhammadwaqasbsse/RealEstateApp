package com.android.immobilien.presentation.viewmodel.propertydetail

import com.android.immobilien.domain.model.Property

/**
 * Represents the UI state for the property detail screen.
 *
 * @param isLoading Indicates if the property details are currently being fetched
 * @param property The detailed property information (null until loaded or if loading fails)
 * @param error Message describing any issues that occurred while loading the property details
 */
data class PropertyDetailState(
    val isLoading: Boolean = false,
    val property: Property? = null,
    val error: String? = null,
)
