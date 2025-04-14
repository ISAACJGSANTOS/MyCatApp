package com.example.mycatapp.navigation

sealed class ScreenNav(val route: String) {
    data object SearchBreed : ScreenNav("search_breed")
    data object FavoriteBreeds : ScreenNav("favorite_breeds")
    data object BreedDetails : ScreenNav("breed_details")
}
