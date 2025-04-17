package com.example.mycatapp.navigation

enum class ScreenNav(val route: String) {
    SEARCH_BREED("searchBreed"),
    FAVORITE_BREEDS("favoriteBreeds"),
    BREED_DETAILS("breedDetails/{name}/{breed}")
}
