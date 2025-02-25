package com.example.greenhub.domain.use_cases.update_plant_name

import com.example.greenhub.data.repository.Repository

class UpdatePlantNameUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(plantId: Int, name: String){
        repository.updatePlantName(plantId, name)
    }
}