package com.example.mycatapp.domain.usecases

import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.repositories.model.OperationResult
import com.example.networking.models.Breed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val catsRepository: CatsRepository) {
    suspend fun execute(): Flow<OperationResult<Array<Breed>>> {
        return catsRepository.getBreeds(45, 0)
    }
}
