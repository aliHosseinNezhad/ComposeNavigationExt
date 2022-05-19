package com.gamapp.compose_navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
fun Navigation(
    init: Pair<String, Route>,
    navigator: Navigator,
    builder: NavigationBuilder.() -> Unit
) {
    val navigateBuilder = rememberSaveable(init, saver = NavigationBuilder.saver()) {
        NavigationBuilder(init = init)
    }.apply {
        setBuilder(builder)
    }
    LaunchedEffect(key1 = navigateBuilder, key2 = navigator) {
        navigator.navFlow.collect {
            with(navigateBuilder) {
                it.navigate()
            }
        }
    }
    val current = navigateBuilder.stack.lastOrNull()
    if (current != null) {
        val key = current.first
        val route = current.second
        navigateBuilder.screens[key]?.let {
            it(route)
        }
    }
    BackHandler(navigateBuilder.stack.size > 1) {
        navigator.popBack()
    }
}




