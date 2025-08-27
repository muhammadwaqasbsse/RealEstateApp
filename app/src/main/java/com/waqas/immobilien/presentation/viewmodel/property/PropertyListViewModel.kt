package com.waqas.immobilien.presentation.viewmodel.property

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waqas.immobilien.domain.usecase.GetPropertiesUseCase
import com.waqas.immobilien.presentation.viewmodel.common.Effect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropertyListViewModel @Inject constructor(
    private val getListings: GetPropertiesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(PropertyListState())
    val state: StateFlow<PropertyListState> = _state

    private val _effect = MutableSharedFlow<Effect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: PropertyListEvent) {
        when (event) {
            is PropertyListEvent.LoadingPropertyList -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = false)
            runCatching { getListings() }
                .onSuccess { listing ->
                    _state.value = PropertyListState(properties = listing)
                    _effect.emit(Effect.ShowToast("Listings loaded!"))
                }
                .onFailure { e ->
                    _state.value = PropertyListState(error = e.message)
                    _effect.emit(Effect.ShowToast("Error loading listings"))
                }
        }
    }
}