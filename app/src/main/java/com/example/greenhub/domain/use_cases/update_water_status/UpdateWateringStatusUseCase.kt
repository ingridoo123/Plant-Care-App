package com.example.greenhub.domain.use_cases.update_water_status

import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant

class UpdateWateringStatusUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(plantId: Int, isWatered: Boolean) {
        repository.updateWateringStatus(plantId, isWatered)
    }
}