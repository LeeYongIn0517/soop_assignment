package com.soop_assignment.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.soop_assignment.app.presentation.navigation.Route
import com.soop_assignment.app.presentation.navigation.SetNavGraph
import com.soop_assignment.app.ui.theme.Soop_assignmentTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Soop_assignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SetNavGraph(
                        navController = navController,
                        startDestination = Route.SearchRepository.name
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Soop_assignmentTheme {
        Greeting("Android")
    }
}
