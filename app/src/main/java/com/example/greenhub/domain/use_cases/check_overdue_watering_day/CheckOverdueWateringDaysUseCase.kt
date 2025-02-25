package com.example.greenhub.domain.use_cases.check_overdue_watering_day

import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant

class CheckOverdueWateringDaysUseCase(
    private val repository: Repository
) {
    operator fun invoke(date: String): List<Plant> {
        return repository.getOverduePlantsToWater(date)
    }
}