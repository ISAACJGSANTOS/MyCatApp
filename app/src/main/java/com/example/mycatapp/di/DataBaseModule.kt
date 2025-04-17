package com.example.mycatapp.di

import android.content.Context
import androidx.room.Room
import com.example.database.FavoriteBreedDatabase
import com.example.database.dao.FavoriteBreedsDao
import com.example.database.dao.FavoriteBreedsDaoImpl
import com.example.database.repository.FavoriteBreedsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideFavoriteBreedsDao(favoriteBreedDatabase: FavoriteBreedDatabase): FavoriteBreedsDao {
        return FavoriteBreedsDaoImpl(favoriteBreedDatabase)
    }

    @Provides
    @Singleton
    fun provideFavoriteBreedsRepository(favoriteBreedsDao: FavoriteBreedsDao): FavoriteBreedsRepository {
        return FavoriteBreedsRepository(favoriteBreedsDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteBreedDatabase(@ApplicationContext context: Context): FavoriteBreedDatabase {
        return Room.databaseBuilder(
            context,
            FavoriteBreedDatabase::class.java,
            "favorite-breed-database"
        ).build()
    }
}
