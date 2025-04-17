package com.example.database.repository

import androidx.room.Insert
import com.example.database.dao.FavoriteBreedsDao
import com.example.database.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

class FavoriteBreedsRepository(private val favoriteBreedsDao: FavoriteBreedsDao) {
    fun getFavoriteBreeds(): Flow<List<BreedEntity>> {
        return favoriteBreedsDao.getFavoriteBreeds()
    }

    @Insert
    fun saveFavoriteBreed(breedEntity: BreedEntity) {
        favoriteBreedsDao.saveFavoriteBreed(breedEntity)
    }

    fun deleteBreed(breedEntity: BreedEntity) {
        favoriteBreedsDao.deleteFavoriteBreed(breedEntity)
    }
}
