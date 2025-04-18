package com.example.mycatapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mycatapp.domain.DashboardViewModel
import com.example.mycatapp.ui.screens.BreedDetailScreen
import com.example.mycatapp.ui.screens.DashboardScreen
import com.example.networking.models.Breed
import com.google.gson.Gson
import java.net.URLEncoder

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: DashboardViewModel?
) {
    val dashboardViewModel = viewModel ?: hiltViewModel()
    NavHost(
        navController = navController,
        startDestination = ScreenNav.SEARCH_BREED.route
    ) {
        composable(ScreenNav.SEARCH_BREED.route) {
            DashboardScreen(
                viewModel = dashboardViewModel,
                navController = navController,
                onTabSelected = { navigateToTab(navController, it) },
                onItemClicked = { navigateToBreedDetails(navController, it) }
            )
        }

        composable(ScreenNav.FAVORITE_BREEDS.route) {
            DashboardScreen(
                viewModel = dashboardViewModel,
                navController = navController,
                onTabSelected = { navigateToTab(navController, it) },
                onItemClicked = { navigateToBreedDetails(navController, it) }
            )
        }

        composable(
            route = ScreenNav.BREED_DETAILS.route,
            arguments = listOf(
                navArgument("breed") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            BreedDetailScreen(
                viewModel = dashboardViewModel,
                breed = backStackEntry.arguments?.getString("breed") ?: ""
            ) {
                navController.popBackStack()
            }
        }
    }
}

private fun navigateToBreedDetails(navController: NavController, breed: Breed) {
    val json = Gson().toJson(breed, Breed::class.java)
    val encodedJson = URLEncoder.encode(json, "UTF-8")
    val route = ScreenNav.BREED_DETAILS.route.replace("{breed}", encodedJson)
    navController.navigate(route)
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        restoreState = true
    }
}
