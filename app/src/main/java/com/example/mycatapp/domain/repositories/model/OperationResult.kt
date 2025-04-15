package com.example.mycatapp.domain.repositories.model

sealed class OperationResult<out T> {
    data class Success<out T>(val data: T) : OperationResult<T>()
    data class Error(val message: String) : OperationResult<Nothing>()
    data class Loading(val boolean: Boolean) : OperationResult<Nothing>()
}
