package com.example.greenhub.domain.use_cases.update_fav_status

import com.example.greenhub.data.repository.Repository

class UpdateFavouritePlantUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(plantId: Int, isFavourite: Boolean){
        repository.updateFavouritePlant(plantId, isFavourite)
    }
}