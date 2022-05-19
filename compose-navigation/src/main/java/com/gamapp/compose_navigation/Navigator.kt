package com.gamapp.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow


sealed class NavigateRequest {
    object PopBack : NavigateRequest()
    class NavigateTo(val navigate: Pair<String, Route>) : NavigateRequest()
}
@Composable
fun rememberNavigator() = remember {
    Navigator()
}
class Navigator {
    @PublishedApi
    internal val navigate: Channel<NavigateRequest> = Channel()
    internal val navFlow: Flow<NavigateRequest> = navigate.receiveAsFlow()

    inline fun <reified T : Route> navigateTo(route: T) {
        val nav = route.getQualifiedName() to route
        navigate.trySend(NavigateRequest.NavigateTo(nav))
    }

    fun popBack() {
        navigate.trySend(NavigateRequest.PopBack)
    }
}