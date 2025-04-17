package com.example.mycatapp.domain.usecases

import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.networking.models.Breed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveFavoriteBreedUseCase @Inject constructor(private val catsRepository: CatsRepository) {
    suspend fun execute(breed: Breed): Flow<OperationStateResult<Array<Breed>>> {
        return catsRepository.saveFavoriteBreed(breed = breed)
    }
}
