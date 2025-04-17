package com.example.mycatapp.domain.repositories.model

sealed class OperationStateResult<out T> {
    data class Success<out T>(val data: T) : OperationStateResult<T>()
    data class Error(val message: String) : OperationStateResult<Nothing>()
    data class Loading(val boolean: Boolean) : OperationStateResult<Nothing>()
}
