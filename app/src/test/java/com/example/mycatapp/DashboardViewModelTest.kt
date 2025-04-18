package com.example.mycatapp

import app.cash.turbine.test
import com.example.mycatapp.domain.DashboardViewModel
import com.example.mycatapp.domain.repositories.model.OperationStateResult
import com.example.mycatapp.domain.usecases.GetCatUseCases
import com.example.networking.models.Breed
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private val getCatUseCases: GetCatUseCases = mockk()
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

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

    private fun <T> successResult(data: T): OperationStateResult<T> =
        OperationStateResult.Success(data)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getCatUseCases.getBreedsUseCase.execute() } returns flow {
            emit(successResult(arrayOf(createBreed())))
        }
        coEvery { getCatUseCases.getFavoriteBreedsUseCase.execute() } returns flow {
            emit(successResult(arrayOf(createBreed(isUserFavorite = true))))
        }
        coEvery { getCatUseCases.searchBreedUseCase.execute(any()) } returns flow {
            emit(successResult(arrayOf(createBreed(id = "Persian", name = "Persian"))))
        }
        coEvery { getCatUseCases.saveFavoriteBreedUseCase.execute(any()) } returns flow {
            emit(successResult(arrayOf(createBreed())))
        }
        coEvery { getCatUseCases.removeFavoriteBreed.execute(any()) } returns flow {
            emit(successResult(arrayOf(createBreed())))
        }

        viewModel = DashboardViewModel(getCatUseCases)
    }

    @Test
    fun `getBreeds emits success when data is retrieved`() = runTest {
        val mockBreeds = arrayOf(createBreed(id = "MaineCoon", name = "Maine Coon"))

        coEvery { getCatUseCases.getBreedsUseCase.execute() } returns flow {
            emit(successResult(mockBreeds))
        }

        viewModel.searchBreed("")

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }

            val successState = awaitItem()
            assert(successState is OperationStateResult.Success)
            successState as OperationStateResult.Success
            assertEquals(1, successState.data.size)
            assertEquals("Maine Coon", successState.data.first().name)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getFavoriteBreeds updates breedsFlow with favorite breeds`() = runTest {
        coEvery { getCatUseCases.getFavoriteBreedsUseCase.execute() } returns flow {
            emit(successResult(arrayOf(createBreed(isUserFavorite = true))))
        }

        viewModel.getFavoriteBreeds()

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }
            val result = awaitItem()
            assert(result is OperationStateResult.Success)
            result as OperationStateResult.Success
            val data = result.data
            assertEquals(1, data.size)
            assertEquals("Siamese", data.first().name)
            assertEquals(true, data.first().isUserFavorite)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getBreeds handles empty response`() = runTest {
        coEvery { getCatUseCases.getBreedsUseCase.execute() } returns flow {
            emit(successResult(emptyArray()))
        }

        viewModel.searchBreed("")

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }
            val result = awaitItem()
            assert(result is OperationStateResult.Success)
            result as OperationStateResult.Success
            assertEquals(0, result.data.size)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getBreeds emits error when Operation fails`() = runTest {
        coEvery { getCatUseCases.getBreedsUseCase.execute() } returns flow {
            emit(OperationStateResult.Error("API failure"))
        }

        viewModel.searchBreed("")

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }
            val errorState = awaitItem()
            assert(errorState is OperationStateResult.Error)

            errorState as OperationStateResult.Error
            assertEquals("API failure", errorState.message)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `searchBreed updates breedsFlow with search results`() = runTest {
        val searchInput = "Siamese"
        coEvery { getCatUseCases.searchBreedUseCase.execute(searchInput) } returns flow {
            emit(successResult(arrayOf(createBreed())))
        }

        viewModel.searchBreed(searchInput)

        viewModel.breedsFlow.test {
            val result = awaitItem()
            assert(result is OperationStateResult.Success)
            result as OperationStateResult.Success
            val data = result.data
            assertEquals(1, data.size)
            assertEquals("Siamese", data.first().name)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `searchBreed with empty input calls getBreeds`() = runTest {
        viewModel.searchBreed("")

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }

            val result = awaitItem()
            assert(result is OperationStateResult.Success)
            result as OperationStateResult.Success
            assertEquals("Siamese", result.data.first().name)

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onFavoriteButtonClicked saves breed when not favorite`() = runTest {
        val nonFavoriteBreed = createBreed(isUserFavorite = false)
        coEvery { getCatUseCases.saveFavoriteBreedUseCase.execute(nonFavoriteBreed) } returns flow {
            emit(successResult(arrayOf(nonFavoriteBreed)))
        }

        viewModel.onFavoriteButtonClicked(nonFavoriteBreed)

        viewModel.breedsFlow.test {
            val result = awaitItem()
            assert(result is OperationStateResult.Success)
            result as OperationStateResult.Success
            val data = result.data
            assertEquals(1, data.size)
            assertEquals(false, data.first().isUserFavorite)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onFavoriteButtonClicked removes breed when already favorite`() = runTest {
        val favoriteBreed = createBreed(id = "Persian", name = "Persian", isUserFavorite = true)
        coEvery { getCatUseCases.removeFavoriteBreed.execute(favoriteBreed) } returns flow {
            emit(successResult(arrayOf(favoriteBreed.copy(isUserFavorite = false))))
        }

        viewModel.onFavoriteButtonClicked(favoriteBreed)

        viewModel.breedsFlow.test {
            val result = awaitItem()
            assert(result is OperationStateResult.Success)
            result as OperationStateResult.Success
            val data = result.data
            assertEquals(1, data.size)
            assertEquals(false, data.first().isUserFavorite)
            cancelAndConsumeRemainingEvents()
        }
    }


    @Test
    fun `onFavoriteButtonClicked emits error when save fails`() = runTest {
        val breed = createBreed(isUserFavorite = false)

        coEvery { getCatUseCases.saveFavoriteBreedUseCase.execute(breed) } returns flow {
            emit(OperationStateResult.Error(Exception("Failed to save breed").toString()))
        }

        viewModel.onFavoriteButtonClicked(breed)

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }
            val result = awaitItem()
            assert(result is OperationStateResult.Error)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `onFavoriteButtonClicked emits error when removing fails`() = runTest {
        val breed = createBreed(isUserFavorite = true)

        coEvery { getCatUseCases.removeFavoriteBreed.execute(breed) } returns flow {
            emit(OperationStateResult.Error(Exception("Failed to remove breed").toString()))
        }

        viewModel.onFavoriteButtonClicked(breed)

        viewModel.breedsFlow.test {
            repeat(1) { awaitItem() }
            val result = awaitItem()
            assert(result is OperationStateResult.Error)
            cancelAndConsumeRemainingEvents()
        }
    }
}
