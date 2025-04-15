package com.example.mycatapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mycatapp.ui.screens.BreedDetailScreen
import com.example.mycatapp.ui.screens.DashboardScreen
import com.example.networking.models.Breed
import com.google.gson.Gson

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenNav.SearchBreed.route
    ) {
        composable(ScreenNav.SearchBreed.route) {
            DashboardScreen(
                navController = navController,
                onTabSelected = { navigateToTab(navController, it) },
                onItemClicked = { navigateToBreedDetails(navController, it)}
            )
        }

        composable(ScreenNav.FavoriteBreeds.route) {
            DashboardScreen(
                navController = navController,
                isFavorite = true,
                onTabSelected = { navigateToTab(navController, it) },
                onItemClicked = { navigateToBreedDetails(navController, it) }
            )
        }

        composable(
            route = ScreenNav.BreedDetails.route,
            arguments = listOf(
                navArgument("breed") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            BreedDetailScreen(
                backStackEntry.arguments?.getString("breed") ?: ""
            ) {
                navController.popBackStack()
            }
        }
    }
}

private fun navigateToBreedDetails(navController: NavController, breed: Breed) {
    val json = Gson().toJson(breed, Breed::class.java)
    val route = ScreenNav.BreedDetails.route.replace("{breed}", json)
    navController.navigate(route)
}

private fun navigateToTab(navController: NavController, route: String){
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        restoreState = true
    }
}


