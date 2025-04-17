package com.example.mycatapp.ui.screens

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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mycatapp.domain.DashboardViewModel
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.mycatapp.navigation.ScreenNav
import com.example.networking.models.Breed

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavController,
    onTabSelected: (route: String) -> Unit,
    onItemClicked: (breed: Breed) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isFavoriteTab = currentRoute == ScreenNav.FAVORITE_BREEDS.route

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedTab = currentRoute ?: ScreenNav.SEARCH_BREED.route,
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
                viewModel = viewModel,
                onItemClicked = onItemClicked,
                isFavoriteTab = isFavoriteTab
            )
        }
    }
}

@Composable
private fun DashboardContent(
    viewModel: DashboardViewModel,
    isFavoriteTab: Boolean = false,
    onItemClicked: (breed: Breed) -> Unit
) {
    val breedsList by viewModel.breedsFlow.collectAsState()
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Title()
        Spacer(modifier = Modifier.height(15.dp))
        if (!isFavoriteTab) SearchBar(viewModel)
        Spacer(modifier = Modifier.height(15.dp))

        when (val result = breedsList) {
            is OperationStateResult.Success -> {
                CatBreedGrid(
                    viewModel = viewModel,
                    catBreeds = result.data,
                    isFavoriteTab = isFavoriteTab,
                    clickAction = onItemClicked
                )
            }

            is OperationStateResult.Error -> {
                ErrorAlertDialog(error = result.message)
            }

            is OperationStateResult.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
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
private fun Title() {
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
