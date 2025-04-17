package com.example.mycatapp.domain.repositories

import com.example.mycatapp.domain.repositories.model.OperationStateResult
import retrofit2.Response
import java.io.IOException
import java.net.UnknownHostException

abstract class BaseRepository {
    protected suspend fun <T> fetch(apiCall: suspend () -> Response<T>): OperationStateResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                OperationStateResult.Success(response.body()!!)
            } else {
                OperationStateResult.Error("API call failed: ${response.message()}")
            }
        } catch (e: IOException) {
            OperationStateResult.Error("No internet connection")
        } catch (e: UnknownHostException) {
            OperationStateResult.Error("Unable to resolve the host. Please check your internet connection.")
        } catch (e: Exception) {
            OperationStateResult.Error("Unexpected error occurred")
        }
    }
}
