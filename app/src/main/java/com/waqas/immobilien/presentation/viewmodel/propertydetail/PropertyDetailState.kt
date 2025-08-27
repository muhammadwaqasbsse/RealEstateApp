package com.waqas.immobilien.presentation.viewmodel.propertydetail

import com.waqas.immobilien.domain.model.Property

data class PropertyDetailState(
    val isLoading: Boolean = false,
    val property: Property? = null,
    val error: String? = null
)