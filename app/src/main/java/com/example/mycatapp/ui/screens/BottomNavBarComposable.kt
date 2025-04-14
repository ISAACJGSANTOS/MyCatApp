package com.example.mycatapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mycatapp.navigation.ScreenNav

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == ScreenNav.SearchBreed.route,
            onClick = { onTabSelected(ScreenNav.SearchBreed.route) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Search Cat Breed") },
            label = { Text("Search Breed") }
        )
        NavigationBarItem(
            selected = selectedTab == ScreenNav.FavoriteBreeds.route,
            onClick = { onTabSelected(ScreenNav.FavoriteBreeds.route) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorite Cat Breeds") },
            label = { Text("Favorite") }
        )
    }
}
