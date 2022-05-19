package com.gamapp.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.parcelize.Parcelize


@Composable
fun Navigation(
    init: Pair<String, Route>,
    navigator: Navigator,
    builder: NavigationBuilder.() -> Unit
) {
    val navigateBuilder = rememberSaveable(saver = NavigationBuilder.saver()) {
        NavigationBuilder(init = init)
    }.apply {
        setBuilder(builder)
    }
    LaunchedEffect(key1 = Unit) {
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

@Preview
@Composable
fun TestNavigator() {
    val navigator = rememberNavigator()
    val route = Main(title = "main", id = 123)
    val key = route.getQualifiedName()
    Navigation(navigator = navigator, init = key to route) {
        composable<Main> { main ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    navigator.navigateTo(Sima(name = "Sima", phoneNumber = 123))
                }) {
                    Text(text = main.title)
                }
            }
        }
        composable<Sima> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    navigator.navigateTo(Mina("mina", phoneNumber = 123))
                }) {
                    Text(text = it.name)
                }
            }
        }
        composable<Mina> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Button(onClick = {
                    navigator.popBack()
                }) {
                    Text(text = it.name)
                }
            }
        }
    }
}

@Parcelize
class Mina(val name: String, val phoneNumber: Int) : Route()

@Parcelize
class Main(val title: String, val id: Long) : Route()

@Parcelize
class Sima(val name: String, val phoneNumber: Int) : Route()

fun main() {

}



