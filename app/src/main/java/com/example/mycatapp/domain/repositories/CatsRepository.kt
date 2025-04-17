package com.example.mycatapp.domain.repositories

import com.example.database.entity.BreedEntity
import com.example.database.repository.FavoriteBreedsRepository
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.networking.api.CatApiService
import com.example.networking.models.Breed
import com.example.networking.network.NetworkClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatsRepository @Inject constructor(
    private val networkClient: NetworkClient,
    private val favoriteBreedsRepository: FavoriteBreedsRepository
) : BaseRepository() {

    private val _breedsFlow = MutableStateFlow<List<Breed>>(emptyList())
    private val favoriteBreedsFlow: Flow<List<Breed>> =
        favoriteBreedsRepository.getFavoriteBreeds()
            .map { breedEntities ->
                breedEntities.map { it.toDomainModel() }
            }

    private val allBreedsFlow: Flow<OperationStateResult<Array<Breed>>> =
        combine(_breedsFlow, favoriteBreedsFlow) { breeds, favorites ->
            val favoriteNames = favorites.map { it.id }.toSet()
            val mergedBreeds = breeds.map { breed ->
                breed.copy(
                    id = breed.id,
                    isUserFavorite = breed.id in favoriteNames
                )
            }
            OperationStateResult.Success(mergedBreeds.toTypedArray())
        }

    suspend fun requestBreeds(limit: Int, page: Int): Flow<OperationStateResult<Array<Breed>>> {
        return flow {
            emit(OperationStateResult.Loading(true))
            val result = fetch {
                networkClient.createService<CatApiService>().requestBreeds(limit, page)
            }
            emitAll(processData(result))
        }
    }

    suspend fun requestFavoriteBreeds(): Flow<OperationStateResult<Array<Breed>>> {
        return flow {
            emit(OperationStateResult.Loading(true))
            favoriteBreedsFlow.collect {
                emit(OperationStateResult.Success(it.toTypedArray()))
            }
        }
    }

    suspend fun searchBreed(searchQuery: String): Flow<OperationStateResult<Array<Breed>>> {
        return flow {
            emit(OperationStateResult.Loading(true))
            val result = fetch {
                networkClient.createService<CatApiService>().requestBreed(query = searchQuery)
            }
            emitAll(processData(result))
        }
    }

    suspend fun saveFavoriteBreed(breed: Breed): Flow<OperationStateResult<Array<Breed>>> {
        return flow {
            try {
                withContext(Dispatchers.IO) {
                    favoriteBreedsRepository.saveFavoriteBreed(breed.toDomainModel())
                }
                emitAll(allBreedsFlow)
            } catch (e: Exception) {
                emit(OperationStateResult.Error(e.message.toString()))
            }
        }
    }

    suspend fun removeFavoriteBreed(breed: Breed): Flow<OperationStateResult<Array<Breed>>> {
        return flow {
            try {
                withContext(Dispatchers.IO) {
                    favoriteBreedsRepository.deleteBreed(breedEntity = breed.toDomainModel())
                }
                emitAll(allBreedsFlow)
            } catch (e: Exception) {
                emit(OperationStateResult.Error(e.message.toString()))
            }
        }
    }

    private suspend fun processData(
        stateResult: OperationStateResult<Array<Breed>>
    ): Flow<OperationStateResult<Array<Breed>>> {
        return flow {
            if (stateResult is OperationStateResult.Success) {
                _breedsFlow.value = stateResult.data.toList()
                emitAll(allBreedsFlow)
            } else {
                emit(stateResult)
            }
        }
    }

    private fun BreedEntity.toDomainModel(): Breed {
        return Breed(
            id = this.id,
            name = this.name,
            lifespan = this.lifespan,
            origin = this.origin,
            temperament = this.temperament,
            description = this.description,
            imageId = this.imageId,
            isUserFavorite = this.isUserFavorite
        )
    }

    private fun Breed.toDomainModel(): BreedEntity {
        return BreedEntity(
            id = this.id,
            name = this.name,
            lifespan = this.lifespan,
            origin = this.origin,
            temperament = this.temperament,
            description = this.description,
            imageId = this.imageId ?: "",
            isUserFavorite = true
        )
    }
}
