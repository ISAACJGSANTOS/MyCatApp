package com.example.mycatapp.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycatapp.domain.repositories.model.OperationResult
import com.example.mycatapp.domain.usecases.GetCatUseCases
import com.example.networking.models.Breed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getCatUseCases: GetCatUseCases
) : ViewModel() {

    private val _breeds = MutableStateFlow<OperationResult<Array<Breed>>>(
        OperationResult.Loading(true)
    )
    val breeds: StateFlow<OperationResult<Array<Breed>>> = _breeds

    init {
        getBreeds()
    }

    fun getBreeds() {
        viewModelScope.launch {
            _breeds.value = OperationResult.Loading(true)
            getCatUseCases.getBreedsUseCase.execute().collect { breeds ->
                _breeds.value = breeds
            }
        }
    }

    fun searchBreed(input: String) {
        _breeds.value = OperationResult.Loading(true)
        viewModelScope.launch {
            getCatUseCases.searchBreedUseCase.execute(input).collect { breeds ->
                _breeds.value = breeds
            }
        }
    }
}
