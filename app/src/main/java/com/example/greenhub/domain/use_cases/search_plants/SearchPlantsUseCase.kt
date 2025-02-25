package com.example.greenhub.domain.use_cases.search_plants

import androidx.paging.PagingData
import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant
import kotlinx.coroutines.flow.Flow

class SearchPlantsUseCase(
    private val repository: Repository
) {
    operator fun invoke(query: String): Flow<PagingData<Plant>> {
        return repository.searchPlants(query)
    }
}