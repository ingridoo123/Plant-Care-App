package com.example.greenhub.domain.use_cases.check_watering_day

import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant

class CheckWateringDaysUseCase(
    private val repository: Repository
) {
    operator fun invoke(todayDate: String): List<Plant> {
        return repository.getPlantsToWater(todayDate)
    }
}