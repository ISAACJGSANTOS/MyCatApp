package com.example.networking

import com.example.networking.api.CatApiService
import com.example.networking.models.Breed
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var catApiService: CatApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        catApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `requestBreeds returns expected breeds on success`() {
        runBlocking {
            val jsonResponse = """[{
                "id": "siamese",
                "name": "Siamese",
                "origin": "Thailand",
                "lifespan": "15",
                "temperament": "Active",
                "description": "Intelligent and social",
                "imageId": "abcd1234",
                "isUserFavorite": false
                }]""".trimIndent()

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(jsonResponse)
            )

            val response: Response<Array<Breed>> = catApiService.requestBreeds(limit = 10, page = 1)

            assertTrue(response.isSuccessful)
            val breeds = response.body()
            assertEquals(1, breeds?.size)
            breeds?.first()?.let {
                assertEquals("siamese", it.id)
                assertEquals("Siamese", it.name)
                assertEquals("Thailand", it.origin)
            }
        }
    }

    @Test
    fun `requestBreeds returns error when API returns error code`() = runBlocking {
        val errorJson = """{ "error": "Not found" }"""
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody(errorJson)
        )

        val response: Response<Array<Breed>> = catApiService.requestBreeds(limit = 10, page = 1)

        assertFalse(response.isSuccessful)
    }

    @Test
    fun `requestBreed returns expected breed on search success`() {
        runBlocking {
            val jsonResponse = """[{
                "id":"persian",
                "name":"Persian",
                "origin":"Iran",
                "lifespan":"14",
                "temperament":"Calm",
                "description":"Quiet and gentle",
                "imageId":"efgh5678",
                "isUserFavorite": 
                false
                }]""".trimIndent()

            mockWebServer.enqueue(
                MockResponse()
                    .setResponseCode(200)
                    .setBody(jsonResponse)
            )

            val response: Response<Array<Breed>> = catApiService.requestBreed(query = "Persian")

            assertTrue(response.isSuccessful)
            val breeds = response.body()
            assertEquals(1, breeds?.size)
            breeds?.first()?.let {
                assertEquals("persian", it.id)
                assertEquals("Persian", it.name)
                assertEquals("Iran", it.origin)
            }
        }
    }

    @Test
    fun `requestBreed returns error when API fails`() = runBlocking {
        val errorJson = """{ "error": "Internal Server Error" }"""
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(errorJson)
        )

        val response: Response<Array<Breed>> = catApiService.requestBreed(query = "Persian")

        assertFalse(response.isSuccessful)
    }
}
