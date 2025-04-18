package com.example.mycatapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun FavoriteIcon(
    iconToggleButtonModifier: Modifier,
    iconModifier: Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = { onClick() },
        modifier = iconToggleButtonModifier
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite",
            tint = if (isFavorite) Color.Red else Color.Gray,
            modifier = iconModifier.testTag("Favorite")
        )
    }
}
