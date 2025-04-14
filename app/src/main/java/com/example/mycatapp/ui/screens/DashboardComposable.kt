package com.example.mycatapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mycatapp.navigation.ScreenNav

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavController,
    catsList: List<String>,
    isFavorite: Boolean = false,
    onTabSelected: (route: String) -> Unit,
    onItemClicked: () -> Unit

) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = currentRoute ?: ScreenNav.SearchBreed.route,
                onTabSelected = { onTabSelected(it) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            DashboardContent(
                catsList = catsList,
                isFavorite = isFavorite,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun DashboardContent(
    catsList: List<String>,
    isFavorite: Boolean = false,
    onItemClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Title()
        Spacer(modifier = Modifier.height(15.dp))
        if (!isFavorite) SearchBar()
        Spacer(modifier = Modifier.height(15.dp))
        CatBreedGrid(
            catBreeds = catsList,
            clickAction = { onItemClicked() }
        )
    }
}


@Composable
fun Title() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "My Cats App")
    }
}
