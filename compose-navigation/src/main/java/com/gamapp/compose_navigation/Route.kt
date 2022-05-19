package com.gamapp.navigation

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

//@Composable
//inline fun <reified T : Route> rememberRoute(
//    vararg key: Any = arrayOf(),
//    route: () -> T
//) = remember(*key, calculation = route)

abstract class Route(val key: String = "") : Parcelable