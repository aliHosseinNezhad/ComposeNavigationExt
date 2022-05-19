package com.gamapp.navigation

import androidx.compose.runtime.Composable

inline fun <reified T : Route> getQualifiedName(key: String): String {
    val kClazz = T::class
    return (kClazz.qualifiedName ?: "") + key
}

inline fun <reified T : Route> T.getQualifiedName(): String {
    val kClazz = T::class
    return (kClazz.qualifiedName ?: "") + key
}

inline fun <reified T : Route> NavigationBuilder.composable(
    key: String = "",
    noinline content: @Composable (T) -> Unit
) {
    val name = getQualifiedName<T>(key)
    screens[name + key] = {
        if (it is T) {
            content(it)
        }
    }
}