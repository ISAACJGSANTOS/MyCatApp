package com.example.mycatapp.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycatapp.domain.repositories.model.OperationStateResult
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

    private val _breedsFlow = MutableStateFlow<OperationStateResult<Array<Breed>>>(
        OperationStateResult.Loading(true)
    )
    val breedsFlow: StateFlow<OperationStateResult<Array<Breed>>> = _breedsFlow

    init {
        getBreeds()
    }

    fun searchBreed(input: String) {
        viewModelScope.launch {
            if (input.isNotEmpty()) {
                getCatUseCases.searchBreedUseCase.execute(input).collect { result ->
                    _breedsFlow.value = result
                }
            } else {
                getBreeds()
            }
        }
    }

    private fun getBreeds() {
        viewModelScope.launch {
            getCatUseCases.getBreedsUseCase.execute().collect { result ->
                _breedsFlow.value = result
            }

        }
    }

    private fun saveFavoriteBreed(breed: Breed) {
        viewModelScope.launch {
            getCatUseCases.saveFavoriteBreedUseCase.execute(breed).collect { result ->
                _breedsFlow.value = result
            }
        }
    }

    private fun removeFavoriteBreed(breed: Breed) {
        viewModelScope.launch {
            getCatUseCases.removeFavoriteBreed.execute(breed).collect { result ->
                _breedsFlow.value = result
            }
        }
    }

    fun onFavoriteButtonClicked(breed: Breed) {
        if (breed.isUserFavorite) {
            removeFavoriteBreed(breed)
        } else {
            saveFavoriteBreed(breed)
        }
    }
}
