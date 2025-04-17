package com.example.mycatapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycatapp.domain.DashboardViewModel
import com.example.networking.models.Breed

@Composable
fun CatBreedGrid(
    viewModel: DashboardViewModel,
    catBreeds: Array<Breed>, isFavoriteTab: Boolean, clickAction: (breed: Breed) -> Unit
) {
    val list = if (isFavoriteTab) catBreeds.filter { it.isUserFavorite } else catBreeds.toList()
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(list.count()) { catBreed ->
            CatBreedItem(
                viewModel = viewModel,
                catBreed = catBreeds[catBreed],
                clickAction = clickAction
            )
        }
    }
}

@Composable
private fun CatBreedItem(
    viewModel: DashboardViewModel,
    catBreed: Breed,
    clickAction: (breed: Breed) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .height(220.dp)
            .fillMaxWidth()
            .clickable { clickAction(catBreed) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
            ) {
                AsyncImage(
                    model = catBreed.imageUrl,
                    contentDescription = catBreed.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            FavoriteIcon(
                iconToggleButtonModifier = Modifier.align(Alignment.TopEnd),
                iconModifier = Modifier.padding(8.dp).size(24.dp),
                isFavorite = catBreed.isUserFavorite,
            ) {
                viewModel.onFavoriteButtonClicked(breed = catBreed)
            }
        }

        Text(
            text = catBreed.name,
            maxLines = 2,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

