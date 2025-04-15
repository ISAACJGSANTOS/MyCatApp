package com.example.mycatapp.domain.usecases

import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.repositories.model.OperationResult
import com.example.networking.models.Breed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBreedUseCase @Inject constructor(private val catsRepository: CatsRepository) {
    suspend fun execute(searchQuery: String): Flow<OperationResult<Array<Breed>>> {
        return catsRepository.searchBreed(searchQuery)
    }
}
