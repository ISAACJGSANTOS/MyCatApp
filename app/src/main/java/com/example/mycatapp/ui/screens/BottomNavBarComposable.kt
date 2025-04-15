package com.example.mycatapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.mycatapp.navigation.ScreenNav

@Composable
fun BottomNavigationBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .drawBehind {
                drawRect(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = -30f,
                        endY = size.height
                    ),
                    topLeft = Offset(0f, -30f)
                )
            },
    ) {
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
