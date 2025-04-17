package com.example.mycatapp.domain.usecases

data class GetCatUseCases(
    val getBreedsUseCase: GetBreedsUseCase,
    val getFavoriteBreedsUseCase: GetFavoriteBreedsUseCase,
    val searchBreedUseCase: SearchBreedUseCase,
    val saveFavoriteBreedUseCase: SaveFavoriteBreedUseCase,
    val removeFavoriteBreed: RemoveFavoriteBreed
)
