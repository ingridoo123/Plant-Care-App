package com.example.greenhub.domain.use_cases.get_all_plants

import androidx.paging.PagingData
import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant
import kotlinx.coroutines.flow.Flow

class GetAllPlantsUseCase(
    private val repository: Repository
) {
    operator fun invoke(): Flow<PagingData<Plant>> {
        return repository.getAllPlants()
    }
}