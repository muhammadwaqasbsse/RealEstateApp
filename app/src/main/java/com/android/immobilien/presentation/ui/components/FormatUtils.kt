package com.android.immobilien.presentation.ui.components

fun formatNullableInt(
    value: Int?,
    default: String = "N/A",
): String = value?.toString() ?: default
