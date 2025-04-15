package com.example.mycatapp.di

import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.usecases.GetBreedsUseCase
import com.example.mycatapp.domain.usecases.GetCatUseCases
import com.example.mycatapp.domain.usecases.SearchBreedUseCase
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
    fun provideCatsRepository(networkClient: NetworkClient): CatsRepository {
        return CatsRepository(networkClient)
    }

    @Provides
    @Singleton
    fun provideCatUseCase(
        getBreedsUseCase: GetBreedsUseCase,
        searchBreedUseCase: SearchBreedUseCase
    ): GetCatUseCases {
        return GetCatUseCases(
            getBreedsUseCase,
            searchBreedUseCase
        )
    }
}
