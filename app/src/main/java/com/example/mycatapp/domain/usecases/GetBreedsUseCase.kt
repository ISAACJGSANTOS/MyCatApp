package com.example.mycatapp.domain.usecases

import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.networking.models.Breed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val catsRepository: CatsRepository) {
    suspend fun execute(page: Int = 0): Flow<OperationStateResult<Array<Breed>>> {
        return catsRepository.requestBreeds(50, page)
    }
}
