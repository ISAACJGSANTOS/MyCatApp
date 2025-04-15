package com.example.mycatapp.domain.repositories

import com.example.mycatapp.domain.repositories.model.OperationResult
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

abstract class BaseRepository {
    protected suspend fun <T> request(apiCall: suspend () -> Response<T>): OperationResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                OperationResult.Success(response.body()!!)
            } else {
                OperationResult.Error("API call failed: ${response.message()}")
            }
        } catch (e: IOException) {
            OperationResult.Error("No internet connection")
        } catch (e: UnknownHostException) {
            OperationResult.Error("Unable to resolve the host. Please check your internet connection.")
        } catch (e: Exception) {
            OperationResult.Error("Unexpected error occurred")
        }
    }
}
