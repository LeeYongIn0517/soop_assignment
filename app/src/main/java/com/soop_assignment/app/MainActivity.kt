package com.soop_assignment.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.soop_assignment.app.presentation.navigation.Route
import com.soop_assignment.app.presentation.navigation.SetNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            SetNavGraph(
                navController = navController,
                startDestination = Route.SearchRepository.name
            )
        }
    }
}
