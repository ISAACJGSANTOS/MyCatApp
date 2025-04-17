package com.example.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_breeds")
class BreedEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "origin")
    var origin: String,
    @ColumnInfo(name = "life_span")
    var lifespan: String,
    @ColumnInfo(name = "temperament")
    var temperament: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "image_id")
    var imageId: String,
    @ColumnInfo(name = "is_user_favorite")
    var isUserFavorite: Boolean
)
