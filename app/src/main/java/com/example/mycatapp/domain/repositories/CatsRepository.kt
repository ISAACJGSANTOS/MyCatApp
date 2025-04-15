package com.example.mycatapp.domain.repositories

import com.example.mycatapp.domain.repositories.model.OperationResult
import com.example.networking.api.CatApiService
import com.example.networking.models.Breed
import com.example.networking.network.NetworkClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CatsRepository @Inject constructor(private val networkClient: NetworkClient) :
    BaseRepository() {

    suspend fun getBreeds(limit: Int, page: Int): Flow<OperationResult<Array<Breed>>> {
        return flow {
            val result = request {
                networkClient.createService<CatApiService>().requestBreeds(limit, page)
            }
            emit(result)
        }
    }

    suspend fun searchBreed(searchQuery: String): Flow<OperationResult<Array<Breed>>> {
        return flow {
            val result = request {
                networkClient.createService<CatApiService>().requestBreed(query = searchQuery)
            }
            emit(result)
        }
    }
}
