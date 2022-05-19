package com.gamapp.compose_navigation

import android.os.Parcelable
import android.util.Log

//@Composable
//inline fun <reified T : Route> rememberRoute(
//    vararg key: Any = arrayOf(),
//    route: () -> T
//) = remember(*key, calculation = route)

abstract class Route(open val key: String = "") : Parcelable

inline fun <reified T : Route> T.getQualifiedName(): String {
    val kClazz = T::class
    return ((kClazz.qualifiedName ?: "") + key).apply {
        Log.i(TAG, "getQualifiedName: $this")
    }
}

inline fun <reified T : Route> T.getKey(): Pair<String, Route> {
    val key = getQualifiedName()
    return key to this
}