package com.example.mycatapp.navigation

sealed class ScreenNav(val route: String) {
    data object SearchBreed : ScreenNav("searchBreed")
    data object FavoriteBreeds : ScreenNav("favoriteBreeds")
    data object BreedDetails : ScreenNav("breedDetails/{name}/{breed}")
}
