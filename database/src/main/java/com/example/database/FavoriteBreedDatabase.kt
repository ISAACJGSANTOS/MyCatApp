package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.FavoriteBreedsDao
import com.example.database.entity.BreedEntity

@Database(entities = [BreedEntity::class], version = 1, exportSchema = false)
abstract class FavoriteBreedDatabase : RoomDatabase() {
    abstract fun favoriteBreedsDao(): FavoriteBreedsDao
}
