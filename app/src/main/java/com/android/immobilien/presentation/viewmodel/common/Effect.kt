package com.android.immobilien.presentation.viewmodel.common

sealed class Effect {
    data class ShowToast(
        val message: String,
    ) : Effect()
}
