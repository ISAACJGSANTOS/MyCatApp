package com.example.mycatapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FavoriteIcon(
    iconToggleButtonModifier: Modifier,
    iconModifier: Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    var checked by remember { mutableStateOf(isFavorite) }
    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            checked = it
            onClick()
        },
        modifier = iconToggleButtonModifier
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = if (checked) Color.Red else Color.Gray,
            modifier = iconModifier
        )
    }
}
