package com.example.mycatapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mycatapp.domain.DashboardViewModel
import com.example.mycatapp.domain.repositories.model.OperationResult
import com.example.mycatapp.navigation.ScreenNav
import com.example.networking.models.Breed

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavController,
    isFavorite: Boolean = false,
    onTabSelected: (route: String) -> Unit,
    onItemClicked: (breed: Breed) -> Unit
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
                isFavorite = isFavorite,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun DashboardContent(
    viewModel: DashboardViewModel = hiltViewModel(),
    isFavorite: Boolean = false,
    onItemClicked: (breed: Breed) -> Unit
) {
    val breedsList by viewModel.breeds.collectAsState()
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Title()
        Spacer(modifier = Modifier.height(15.dp))
        if (!isFavorite) SearchBar()
        Spacer(modifier = Modifier.height(15.dp))

        when (val result = breedsList) {
            is OperationResult.Success -> {
                CatBreedGrid(
                    catBreeds = result.data,
                    clickAction = onItemClicked
                )
            }

            is OperationResult.Error -> {
                ErrorAlertDialog(error = result.message)
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center) // Center the progress indicator
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun Title() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "My Cats App",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
        )
    }
}
