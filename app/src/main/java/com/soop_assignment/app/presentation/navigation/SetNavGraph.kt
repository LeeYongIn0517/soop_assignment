package com.soop_assignment.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun SetNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        searchRepositoryScreen(navigateToRepository = { userName, repository ->
            navController.navigateToRepository(
                userName,
                repository
            )
        })
        repositoryScreen(navController::popBackStack)
    }
}
