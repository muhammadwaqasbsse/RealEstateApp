package com.android.immobilien.presentation.viewmodel.propertydetail

import com.android.immobilien.domain.model.Property

data class PropertyDetailState(
    val isLoading: Boolean = false,
    val property: Property? = null,
    val error: String? = null,
)
