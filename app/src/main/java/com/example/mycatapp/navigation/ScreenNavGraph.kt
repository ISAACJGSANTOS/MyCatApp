package com.example.mycatapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mycatapp.ui.screens.BreedDetailScreen
import com.example.mycatapp.ui.screens.DashboardScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenNav.SearchBreed.route
    ) {
        composable(ScreenNav.SearchBreed.route) {
            val catsList = mutableListOf<String>()
            for (cat in 1..50) {
                catsList.add("cat$cat")
            }
            DashboardScreen(
                navController = navController,
                catsList = catsList,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onItemClicked = {
                    navController.navigate(ScreenNav.BreedDetails.route)
                }
            )
        }

        composable(ScreenNav.FavoriteBreeds.route) {
            val catsList = mutableListOf<String>()
            for (cat in 1..10) {
                catsList.add("cat$cat")
            }
            DashboardScreen(
                navController = navController,
                catsList = catsList,
                isFavorite = true,
                onTabSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onItemClicked = {
                    navController.navigate(ScreenNav.BreedDetails.route)
                }
            )
        }

        composable(ScreenNav.BreedDetails.route) {
            BreedDetailScreen() {
                navController.popBackStack()
            }
        }
    }
}

