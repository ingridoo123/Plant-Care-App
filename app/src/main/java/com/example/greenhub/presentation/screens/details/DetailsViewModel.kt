package com.example.greenhub.presentation.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: UseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _selectedPlant: MutableStateFlow<Plant?> = MutableStateFlow(null)
    val selectedPlant: StateFlow<Plant?> = _selectedPlant

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val plantId = savedStateHandle.get<Int>("plantId")
            _selectedPlant.value = plantId?.let { useCase.getSelectedPlantUseCase(plantId) }
            _selectedPlant.value?.type?.let {  Log.d("Plant",it)}

        }
    }

    fun updateFavouriteScreen(plantId: Int, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateFavouritePlantUseCase(plantId, isFavourite)
            _selectedPlant.value = _selectedPlant.value?.copy(favourite = true)
        }
    }

    fun updateWateringDate(plantId: Int, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateWateringDateUseCase(plantId, date)
            _selectedPlant.value = _selectedPlant.value?.copy(date = date)
        }
    }

}