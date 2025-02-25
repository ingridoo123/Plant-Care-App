package com.example.greenhub.domain.use_cases.update_watering_date

import com.example.greenhub.data.repository.Repository

class UpdateWateringDateUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(plantId: Int, date: String) {

        repository.updateWateringDate(plantId,date)
    }
}