package com.waqas.immobilien.presentation.ui.components

fun formatNullableInt(value: Int?, default: String = "N/A"): String {
    return value?.toString() ?: default
}