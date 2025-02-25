package com.example.greenhub.data.repository

import androidx.paging.PagingSource
import com.example.greenhub.data.local.PlantsDatabase
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.repository.LocalDataSource

class LocalDataSourceImpl(plantsDatabase: PlantsDatabase): LocalDataSource {

    private val plantDao = plantsDatabase.plantDao()

    override suspend fun getSelectedHero(plantId: Int): Plant {
        return plantDao.getSelectedPlant(plantId)
    }

    override fun getPlantsSortedByNameAscending(): PagingSource<Int, Plant> {
        return plantDao.getAllPlantsSortedByNameAscending()
    }

    override suspend fun updateFavouritePlant(plantId: Int, isFavourite: Boolean) {
        plantDao.updateFavouriteScreen(plantId, isFavourite)
    }

    override suspend fun updateWateringStatus(plantId: Int, isWatered: Boolean) {
        plantDao.updateWateringStatus(plantId, isWatered)
    }

    override suspend fun updatePlantName(plantId: Int, name: String) {
        plantDao.updatePlantName(plantId, name)
    }

    override suspend fun updateWateringDate(plantId: Int, date: String) {
        plantDao.updateWateringDate(plantId, date)
    }

    override fun getPlantsToWater(todayDate: String): List<Plant> {
        return plantDao.getPlantsToWater(todayDate)
    }

    override fun getOverduePlantToWater(date: String): List<Plant> {
        return plantDao.getOverduePlantsToWater(date)
    }

    override fun getFavouritePlants(): List<Plant> {
        return plantDao.getFavouritePlants()
    }
}