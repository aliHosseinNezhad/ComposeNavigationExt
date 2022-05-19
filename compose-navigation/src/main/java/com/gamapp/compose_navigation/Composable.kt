package com.gamapp.compose_navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder

const val TAG = "NavigationComposeEXTAG"


inline fun <reified T : Route> getQualifiedName(key: String): String {
    val kClazz = T::class
    return (kClazz.qualifiedName ?: "") + key
}



inline fun <reified T : Route> NavigationBuilder.composable(
    key: String = "",
    noinline content: @Composable (T) -> Unit
) {
    val finalKey = getQualifiedName<T>(key)
    Log.i(TAG, "composable: $finalKey")
    screens[finalKey] = {
        if (it is T) {
            content(it)
        }
    }
}