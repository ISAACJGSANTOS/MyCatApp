package com.example.networking.api

import com.example.networking.models.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun requestBreeds(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<Array<Breed>>

    @GET("breeds/search")
    suspend fun requestBreed(@Query("q") query: String): Response<Array<Breed>>
}
