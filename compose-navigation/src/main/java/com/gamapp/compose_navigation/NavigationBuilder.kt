package com.gamapp.navigation


import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap

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

    internal fun NavigateRequest.navigate(): Unit = when (this) {
        is NavigateRequest.PopBack -> {
            popBack()
        }
        is NavigateRequest.NavigateTo -> {
            navigate.navigateTo()
        }
    }

    private fun Pair<String, Route>.navigateTo() {
        stack += this
    }

    private fun popBack() {
        stack.removeLastOrNull()
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