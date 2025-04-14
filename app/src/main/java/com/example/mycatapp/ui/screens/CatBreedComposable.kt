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
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CatBreedGrid(catBreeds: List<String>, clickAction: () -> Unit) {
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
fun CatBreedItem(catBreed: String, clickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { clickAction() },
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
                    model = "https://picsum.photos/200/300",
                    contentDescription = catBreed,
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
            text = catBreed,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
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
            Icons.Filled.Star,
            contentDescription = "Favorite",
            tint = if (checked) Color.Yellow else Color.Gray,
            modifier = Modifier
                .padding(8.dp)
                .size(24.dp)
        )
    }
}