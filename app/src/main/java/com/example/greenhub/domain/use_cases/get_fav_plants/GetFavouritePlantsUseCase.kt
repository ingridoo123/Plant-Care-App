package com.example.greenhub.domain.use_cases.get_fav_plants

import androidx.paging.PagingSource
import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.model.Plant

class GetFavouritePlantsUseCase(
    private val repository: Repository
) {
    operator fun invoke(): List<Plant> {
        return repository.getFavouritePlants()
    }
}