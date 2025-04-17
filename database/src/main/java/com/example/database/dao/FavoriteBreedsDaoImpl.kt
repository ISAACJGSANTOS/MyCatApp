package com.example.database.dao

import androidx.room.Dao
import com.example.database.FavoriteBreedDatabase
import com.example.database.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
class FavoriteBreedsDaoImpl(private val favoriteBreedDatabase: FavoriteBreedDatabase) :
    FavoriteBreedsDao {
    override fun getFavoriteBreeds(): Flow<List<BreedEntity>> {
        return favoriteBreedDatabase.favoriteBreedsDao().getFavoriteBreeds()
    }

    override fun saveFavoriteBreed(breedEntity: BreedEntity) {
        favoriteBreedDatabase.favoriteBreedsDao().saveFavoriteBreed(breedEntity)
    }

    override fun deleteFavoriteBreed(breedEntity: BreedEntity) {
        favoriteBreedDatabase.favoriteBreedsDao().deleteFavoriteBreed(breedEntity)
    }
}
