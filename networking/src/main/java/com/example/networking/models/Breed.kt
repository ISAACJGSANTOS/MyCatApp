package com.example.networking.models

import com.google.gson.annotations.SerializedName

data class Breed(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    var origin: String,
    @SerializedName("temperament")
    var temperament: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("reference_image_id")
    var imageId: String? = "",
    var isUserFavorite: Boolean,
) {
    val imageUrl: String
        get() = "https://cdn2.thecatapi.com/images/$imageId.jpg"
}
