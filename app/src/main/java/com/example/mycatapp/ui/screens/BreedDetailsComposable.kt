package com.example.mycatapp.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailScreen(
    onPressBack: () -> Unit,
) {
    BackHandler(onBack = onPressBack)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cat Breed Details") },
                navigationIcon = {
                    IconButton(onClick = onPressBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FavoriteIcon(isFavorite = true) {
                        //TODO handle action add to favorites
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {

            AsyncImage(
                model = "https://picsum.photos/200/300",
                contentDescription = "Breed Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Origin: Example origin")
            Text(text = "Temperament: Friendly")
            Text(text = "Description: A friendly and playful breed of cats.")
        }
    }
}

@Composable
fun FavoriteIcon(isFavorite: Boolean, onClick: () -> Unit) {
    var checked by remember { mutableStateOf(isFavorite) }
    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            checked = it
            onClick()
        },
    ) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "Favorite",
            tint = if (checked) Color.Yellow else Color.Gray,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        )
    }
}
