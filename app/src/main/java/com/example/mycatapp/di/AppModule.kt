package com.example.mycatapp.di

import com.example.database.repository.FavoriteBreedsRepository
import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.usecases.GetBreedsUseCase
import com.example.mycatapp.domain.usecases.GetCatUseCases
import com.example.mycatapp.domain.usecases.RemoveFavoriteBreed
import com.example.mycatapp.domain.usecases.SearchBreedUseCase
import com.example.mycatapp.domain.usecases.SaveFavoriteBreedUseCase
import com.example.networking.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCatsRepository(
        networkClient: NetworkClient,
        favoriteBreedsRepository: FavoriteBreedsRepository
    ): CatsRepository {
        return CatsRepository(
            networkClient,
            favoriteBreedsRepository
        )
    }

    @Provides
    @Singleton
    fun provideCatUseCase(
        getBreedsUseCase: GetBreedsUseCase,
        searchBreedUseCase: SearchBreedUseCase,
        saveFavoriteBreedUseCase: SaveFavoriteBreedUseCase,
        removeFavoriteBreed: RemoveFavoriteBreed
    ): GetCatUseCases {
        return GetCatUseCases(
            getBreedsUseCase,
            searchBreedUseCase,
            saveFavoriteBreedUseCase,
            removeFavoriteBreed
        )
    }
}
