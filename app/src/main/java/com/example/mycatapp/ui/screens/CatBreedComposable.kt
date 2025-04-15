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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.networking.models.Breed

@Composable
fun CatBreedGrid(catBreeds: Array<Breed>, clickAction: (breed: Breed) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(catBreeds.count()) { catBreed ->
            CatBreedItem(catBreed = catBreeds[catBreed], clickAction)
        }
    }
}

@Composable
fun CatBreedItem(catBreed: Breed, clickAction: (breed: Breed) -> Unit) {
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
                modifier = Modifier.align(Alignment.TopEnd),
                isFavorite = true
            ) {
                //TODO: Handle click to add breed to favorites
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

@Composable
fun FavoriteIcon(modifier: Modifier = Modifier, isFavorite: Boolean, onClick: () -> Unit) {
    var checked by remember { mutableStateOf(isFavorite) }
    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            checked = it
            onClick()
        },
        modifier = modifier
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = if (checked) Color.Red else Color.Gray,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
        )
    }
}
