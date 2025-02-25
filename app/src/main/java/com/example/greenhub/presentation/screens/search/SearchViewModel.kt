package com.example.greenhub.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {
    val getAllPlants = useCases.getAllPlantsUseCase()

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedPlants = MutableStateFlow<PagingData<Plant>>(PagingData.empty())
    val searchedPlants = _searchedPlants

    var sortedPlants: List<Plant>? = null

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        sortedPlants = null
    }

    fun searchPlants(query: String) {
        viewModelScope.launch {
            useCases.searchPlantsUseCase.invoke(query).cachedIn(viewModelScope).collect {
                _searchedPlants.value = it
            }
        }
    }



}