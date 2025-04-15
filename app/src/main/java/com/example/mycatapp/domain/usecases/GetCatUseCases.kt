package com.example.mycatapp.domain.usecases

data class GetCatUseCases(
    val getBreedsUseCase: GetBreedsUseCase,
    val searchBreedUseCase: SearchBreedUseCase
)
