package com.gamapp.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import com.gamapp.compose_navigation.*
import com.gamapp.myapplication.ui.theme.MyApplicationTheme
import kotlinx.parcelize.Parcelize
import java.lang.Appendable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                App()
            }
        }
    }
}

@Parcelize
class Main(
    val name: String,
) : Route()

@Parcelize
data class SecondScreen(
    val name: String,
    override val key: String = ""
) : Route(key = key)


@Composable
fun App() {
    val nav = rememberNavigator()
    Navigation(init = Main("main").getKey(), navigator = nav) {
        composable<Main> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            ) {
                Button(onClick = {
                    nav.navigateTo(SecondScreen("secondScreen"))
                }, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = it.name)
                }
            }
        }
        composable<SecondScreen>(key = "keyed") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {
                Button(onClick = {
                    nav.popBack()
                }, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = it.name)
                }
            }

        }
        composable<SecondScreen> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ) {
                Button(onClick = {
                    nav.navigateTo(SecondScreen("Nima", "keyed"))
                }, modifier = Modifier.align(Alignment.Center)) {
                    Text(text = it.name)
                }
            }
        }
    }
}