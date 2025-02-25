package com.example.greenhub.presentation.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.room.Dao
import com.example.greenhub.data.local.dao.PlantDao
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val _favouritePlants = MutableStateFlow<List<Plant>>(emptyList())
    val favouritePlants = _favouritePlants

    var sortedPlants: List<Plant>? = null

    init {
        fetchFavouritePlants()
    }

    fun fetchFavouritePlants() {
        viewModelScope.launch(Dispatchers.IO) {
            val favourites = useCases.getFavouritePlantsUseCase()
            _favouritePlants.value = favourites
        }
    }




}