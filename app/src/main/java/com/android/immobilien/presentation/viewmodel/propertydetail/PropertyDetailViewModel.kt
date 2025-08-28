package com.android.immobilien.presentation.viewmodel.propertydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.immobilien.domain.usecase.GetPropertyDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyDetailViewModel
    @Inject
    constructor(
        private val getListingDetail: GetPropertyDetailUseCase,
    ) : ViewModel() {
        private val _state = MutableStateFlow(PropertyDetailState())
        val state: StateFlow<PropertyDetailState> = _state

        /**
         * Initiates loading of detailed property information.
         *
         * @param id The unique identifier of the property to load details for
         */
        fun load(id: Int) {
            viewModelScope.launch {
                _state.value = PropertyDetailState(isLoading = true)
                runCatching { getListingDetail(id) }
                    .onSuccess { _state.value = PropertyDetailState(property = it) }
                    .onFailure { _state.value = PropertyDetailState(error = it.message) }
            }
        }
    }
