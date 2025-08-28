package com.waqas.immobilien.presentation.viewmodel.common

sealed class Effect {
    data class ShowToast(
        val message: String,
    ) : Effect()
}

