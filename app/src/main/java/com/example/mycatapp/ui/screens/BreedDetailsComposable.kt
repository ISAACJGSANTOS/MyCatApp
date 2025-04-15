package com.example.mycatapp.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.networking.models.Breed
import com.google.gson.Gson

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailScreen(
    breed: String,
    onPressBack: () -> Unit,
) {
    BackHandler(onBack = onPressBack)
    val gson = Gson()
    val breedInfo = gson.fromJson(breed, Breed::class.java)

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = breedInfo.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onPressBack) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {
                        FavoriteIcon(isFavorite = true) {
                            //TODO handle action add to favorites
                        }
                    },
                )
            }
        }
    ) { innerPadding ->
        ScreenContent(breedInfo = breedInfo, padding = innerPadding)
    }
}

@Composable
private fun ScreenContent(breedInfo: Breed, padding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            AsyncImage(
                model = breedInfo.imageUrl,
                contentDescription = "Breed Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Content(breedInfo = breedInfo)
    }
}

@Composable
private fun Content(breedInfo: Breed) {
    Spacer(modifier = Modifier.height(16.dp))
    InfoLine(label = "Breed Name", value = breedInfo.name)
    Spacer(modifier = Modifier.height(8.dp))
    InfoLine(label = "Origin", value = breedInfo.origin)
    Spacer(modifier = Modifier.height(8.dp))
    InfoLine(label = "Temperament", value = breedInfo.temperament)
    Spacer(modifier = Modifier.height(8.dp))
    InfoLine(label = "Description", value = breedInfo.description)

}

@Composable
fun InfoLine(label: String, value: String) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            ) {
                append("$label ")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                append(value)
            }
        },
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
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
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = if (checked) Color.Red else Color.Gray,
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        )
    }
}
