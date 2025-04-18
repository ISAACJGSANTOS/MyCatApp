package com.example.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.database.entity.BreedEntity
import com.example.database.repository.FavoriteBreedsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FavoriteBreedDatabaseTest {

    private lateinit var database: FavoriteBreedDatabase
    private lateinit var repository: FavoriteBreedsRepository

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteBreedDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        repository = FavoriteBreedsRepository(database.favoriteBreedsDao())
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testSaveAndGetFavoriteBreed() = runBlocking {
        val breedEntity = BreedEntity(
            id = "siamese",
            name = "Siamese",
            lifespan = "15 years",
            origin = "Thailand",
            temperament = "Playful",
            description = "Friendly and social cat",
            imageId = "img1",
            isUserFavorite = true
        )

        repository.saveFavoriteBreed(breedEntity)

        val favorites = repository.getFavoriteBreeds().first()
        assertEquals(1, favorites.size)
        assertEquals("Siamese", favorites.first().name)
    }

    @Test
    fun testDeleteFavoriteBreed() = runBlocking {
        val breedEntity = BreedEntity(
            id = "siamese",
            name = "Siamese",
            lifespan = "15 years",
            origin = "Thailand",
            temperament = "Playful",
            description = "Friendly and social cat",
            imageId = "img1",
            isUserFavorite = true
        )
        repository.saveFavoriteBreed(breedEntity)
        var favorites = repository.getFavoriteBreeds().first()
        assertEquals(1, favorites.size)

        repository.deleteBreed(breedEntity)
        favorites = repository.getFavoriteBreeds().first()
        assertTrue(favorites.isEmpty())
    }
}
