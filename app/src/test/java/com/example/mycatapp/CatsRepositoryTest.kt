package com.example.mycatapp

import app.cash.turbine.test
import com.example.database.entity.BreedEntity
import com.example.database.repository.FavoriteBreedsRepository
import com.example.mycatapp.domain.repositories.CatsRepository
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.networking.api.CatApiService
import com.example.networking.models.Breed
import com.example.networking.network.NetworkClient
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CatsRepositoryTest {

    private lateinit var repository: CatsRepository
    private val networkClient: NetworkClient = mockk()
    private val favoriteBreedsRepository: FavoriteBreedsRepository = mockk()
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { favoriteBreedsRepository.getFavoriteBreeds() } returns flow {
            emit(emptyList())
        }
        repository = CatsRepository(networkClient, favoriteBreedsRepository)
    }

    @Test
    fun `requestBreeds emits success when API returns data`() = runTest {
        val mockBreeds = arrayOf(createBreed())

        coEvery {
            networkClient.createService<CatApiService>().requestBreeds(any(), any())
        } returns Response.success(mockBreeds)

        val resultFlow = repository.requestBreeds(limit = 10, page = 1)

        resultFlow.test {
            val loadingState = awaitItem()
            assert(loadingState is OperationStateResult.Loading)

            val successState = awaitItem()
            assert(successState is OperationStateResult.Success)
            successState as OperationStateResult.Success
            assertEquals(1, successState.data.size)
            assertEquals("Siamese", successState.data.first().name)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `requestBreeds emits error when API fails`() = runTest {
        coEvery {
            networkClient.createService<CatApiService>().requestBreeds(any(), any())
        } throws IOException("Network error")

        val resultFlow = repository.requestBreeds(limit = 10, page = 1)

        resultFlow.test {
            val loadingState = awaitItem()
            assert(loadingState is OperationStateResult.Loading)

            val errorState = awaitItem()
            assert(errorState is OperationStateResult.Error)
            errorState as OperationStateResult.Error
            assertEquals("No internet connection", errorState.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `requestFavoriteBreeds emits loading then success`() = runTest {
        val favoriteEntity = createEntityBreed()
        coEvery { favoriteBreedsRepository.getFavoriteBreeds() } returns flow {
            emit(listOf(favoriteEntity))
        }
        repository = CatsRepository(networkClient, favoriteBreedsRepository)

        val resultFlow = repository.requestFavoriteBreeds()
        resultFlow.test {
            val loadingState = awaitItem()
            assertTrue(loadingState is OperationStateResult.Loading)

            val successState = awaitItem()
            assertTrue(successState is OperationStateResult.Success)
            successState as OperationStateResult.Success
            assertEquals(1, successState.data.size)
            assertEquals("Siamese", successState.data.first().name)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `searchBreed emits success when API returns data`() = runTest {
        val mockBreeds = arrayOf(createBreed(id = "MaineCoon", name = "Maine Coon"))

        coEvery {
            networkClient.createService<CatApiService>().requestBreed(any())
        } returns Response.success(mockBreeds)

        val resultFlow = repository.searchBreed("Maine Coon")

        resultFlow.test {
            val loadingState = awaitItem()
            assert(loadingState is OperationStateResult.Loading)

            val successState = awaitItem()
            assert(successState is OperationStateResult.Success)
            successState as OperationStateResult.Success
            assertEquals(1, successState.data.size)
            assertEquals("Maine Coon", successState.data.first().name)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `searchBreed emits error when API fails`() = runTest {
        coEvery {
            networkClient.createService<CatApiService>().requestBreed(any())
        } throws IOException("Network error")

        val resultFlow = repository.searchBreed("Siamese")

        resultFlow.test {
            val loadingState = awaitItem()
            assert(loadingState is OperationStateResult.Loading)

            val errorState = awaitItem()
            assert(errorState is OperationStateResult.Error)
            errorState as OperationStateResult.Error
            assertEquals("No internet connection", errorState.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `saveFavoriteBreed stores breed and updates flow`() = runTest {
        val breed = createBreed(isUserFavorite = false)

        coEvery { favoriteBreedsRepository.saveFavoriteBreed(any()) } just runs

        val resultFlow = repository.saveFavoriteBreed(breed)

        resultFlow.test {
            val successState = awaitItem()
            assert(successState is OperationStateResult.Success)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `removeFavoriteBreed emits success when breed is removed`() = runTest {
        val breed = createBreed(isUserFavorite = true)

        coEvery { favoriteBreedsRepository.deleteBreed(any()) } just runs

        val resultFlow = repository.removeFavoriteBreed(breed)

        resultFlow.test {
            val successState = awaitItem()
            assert(successState is OperationStateResult.Success)
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun createBreed(
        id: String = "Siamese",
        name: String = "Siamese",
        origin: String = "",
        lifespan: String = "",
        temperament: String = "",
        description: String = "",
        imageId: String = "url1",
        isUserFavorite: Boolean = false
    ) = Breed(
        id = id,
        name = name,
        origin = origin,
        lifespan = lifespan,
        temperament = temperament,
        description = description,
        imageId = imageId,
        isUserFavorite = isUserFavorite
    )

    private fun createEntityBreed(
        id: String = "Siamese",
        name: String = "Siamese",
        origin: String = "",
        lifespan: String = "",
        temperament: String = "",
        description: String = "",
        imageId: String = "url1",
        isUserFavorite: Boolean = true
    ) = BreedEntity(
        id = id,
        name = name,
        origin = origin,
        lifespan = lifespan,
        temperament = temperament,
        description = description,
        imageId = imageId,
        isUserFavorite = isUserFavorite
    )
}
