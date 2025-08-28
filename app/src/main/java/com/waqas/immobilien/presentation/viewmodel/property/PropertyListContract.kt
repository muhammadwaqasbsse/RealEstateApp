package com.waqas.immobilien.presentation.viewmodel.property

import com.waqas.immobilien.domain.model.Property

data class PropertyListState(
    val isLoading: Boolean = false,
    val properties: List<Property> = emptyList(),
    val error: String? = null,
)

sealed class PropertyListEvent {
    data object LoadingPropertyList : PropertyListEvent()
}
