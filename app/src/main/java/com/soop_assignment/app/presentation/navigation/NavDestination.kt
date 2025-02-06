package com.soop_assignment.app.presentation.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.soop_assignment.app.presentation.view.RepositoryScreen
import com.soop_assignment.app.presentation.view.SearchRepositoryScreen
import com.soop_assignment.app.ui.theme.Typography

fun NavController.navigateToSearchRepository() = navigate("${Route.SearchRepository}")
fun NavController.navigateToRepository(userName: String, repository: String) =
    navigate("${Route.Repository}/${userName}/${repository}")

fun NavGraphBuilder.searchRepositoryScreen(navigateToRepository: (userName: String, repository: String) -> Unit) {
    composable(route = "${Route.SearchRepository}") {
        SearchRepositoryScreen(navigateToRepository = navigateToRepository)
    }
}

fun NavGraphBuilder.repositoryScreen(navigateToBack: () -> Unit) {
    composable(
        route = "${Route.Repository}/{userName}/{repository}",
        arguments = listOf(navArgument("userName") { type = NavType.StringType },
            navArgument("repository") { type = NavType.StringType })
    ) {
        val userName = it.arguments?.getString("userName")
        val repository = it.arguments?.getString("repository")
        if (userName != null && repository != null) {
            RepositoryScreen(userName = userName, repository = repository, navigateToBack)
        } else {
            Text("유효하지 않은 상세페이지입니다.", style = Typography.titleMedium)
        }
    }
}
