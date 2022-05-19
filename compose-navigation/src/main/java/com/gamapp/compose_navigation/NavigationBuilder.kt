package com.gamapp.compose_navigation


import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver

class NavigationBuilder {
    @PublishedApi
    internal val screens: MutableMap<String, @Composable (Route) -> Unit> = mutableMapOf()

    @PublishedApi
    internal val stack = mutableStateListOf<Pair<String, Route>>()

    internal fun setBuilder(builder: NavigationBuilder.() -> Unit) {
        screens.clear()
        this.builder()
    }

    constructor(init: Pair<String, Route>) {
        stack.add(init)
    }

    constructor(
        stack: List<Pair<String, Route>>
    ) {
        this.stack.addAll(stack)
    }

    internal fun NavigateRequest.navigate(saveableStateHolder: SaveableStateHolder): Unit =
        when (this) {
            is NavigateRequest.PopBack -> {
                popBack(saveableStateHolder)
            }
            is NavigateRequest.NavigateTo -> {
                navigate.navigateTo()
            }
        }

    private fun Pair<String, Route>.navigateTo() {
        stack += this
    }

    private fun popBack(saveableStateHolder: SaveableStateHolder) {
        stack.removeLastOrNull()?.let {
            saveableStateHolder.removeState(it.first)
        }
    }

    companion object {
        fun saver(): Saver<NavigationBuilder, Any> {
            return mapSaver(
                save = {
                    it.stack.toMap()
                },
                restore = {
                    val stack = it.mapNotNull { entry ->
                        if (entry.value != null && entry.value is Route)
                            entry.key to entry.value as Route
                        else null
                    }
                    NavigationBuilder(stack)
                }
            )
        }

    }
}