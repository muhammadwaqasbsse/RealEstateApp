package com.android.immobilien.presentation.viewmodel.property

import com.android.immobilien.domain.model.Property

data class PropertyListState(
    val isLoading: Boolean = false,
    val properties: List<Property> = emptyList(),
    val error: String? = null,
)

sealed class PropertyListEvent {
    data object LoadingPropertyList : PropertyListEvent()
}
