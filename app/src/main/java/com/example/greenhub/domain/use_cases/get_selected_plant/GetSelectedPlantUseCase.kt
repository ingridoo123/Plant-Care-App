package com.example.greenhub.domain.use_cases.get_selected_plant

import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant

class GetSelectedPlantUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(plantId: Int): Plant {
        return repository.getSelectedPlant(plantId)
    }
}