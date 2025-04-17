package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBreedsDao {
    @Query("SELECT * FROM favorite_breeds")
    fun getFavoriteBreeds(): Flow<List<BreedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteBreed(breedEntity: BreedEntity)

    @Delete
    fun deleteFavoriteBreed(breedEntity: BreedEntity)
}
