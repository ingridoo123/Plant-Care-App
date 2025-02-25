package com.example.greenhub.domain.repository

import androidx.paging.PagingSource
import com.example.greenhub.domain.model.Plant

interface LocalDataSource {
    suspend fun getSelectedHero(plantId: Int): Plant
    fun getPlantsSortedByNameAscending(): PagingSource<Int, Plant>
    suspend fun updateFavouritePlant(plantId: Int, isFavourite: Boolean)

    suspend fun updateWateringStatus(plantId: Int, isWatered: Boolean)

    suspend fun updatePlantName(plantId: Int, name: String)

    suspend fun updateWateringDate(plantId: Int, date: String)

    fun getPlantsToWater(todayDate: String): List<Plant>

    fun getOverduePlantToWater(date: String): List<Plant>

    fun getFavouritePlants(): List<Plant>
}