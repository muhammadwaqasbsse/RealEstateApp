package com.android.immobilien.utils

import android.content.Context

/**
 * Extension function to format a nullable [Int] as a [String].
 *
 * If the value is `null`, returns a default string from resources
 * (localized), e.g., "N/A".
 *
 * @param context Context to access string resources
 * @param defaultResId Resource ID of the default string to use when value is null
 * @return String representation of the value, or localized default if null
 */
fun Int?.formatNullable(
    context: Context,
    defaultResId: Int,
): String = this?.toString() ?: context.getString(defaultResId)
