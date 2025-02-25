package com.example.greenhub.data.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.repository.DataStoreOperations
import com.example.greenhub.domain.repository.LocalDataSource
import com.example.greenhub.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val local: LocalDataSource,
    private val dataStore: DataStoreOperations,
    private val remote: RemoteDataSource
) {

    fun getAllPlants(): Flow<PagingData<Plant>> {
        return remote.getAllPlants()
    }


    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

    suspend fun saveLoginState(completed: Boolean) {
        dataStore.saveLoginState(completed = completed)
    }

    fun readLoginState(): Flow<Boolean> {
        return dataStore.readLoginState()
    }

    fun searchPlants(query: String): Flow<PagingData<Plant>> {
        return remote.searchPlants(query)
    }

    suspend fun getSelectedPlant(plantId: Int): Plant {
        return local.getSelectedHero(plantId)
    }


    suspend fun updateFavouritePlant(plantId: Int, isFavourite: Boolean) {
        local.updateFavouritePlant(plantId, isFavourite)
    }

    suspend fun updatePlantName(plantId: Int, name: String) {
        local.updatePlantName(plantId,name)
    }

    suspend fun updateWateringStatus(plantId: Int, isWatered: Boolean) {
        local.updateWateringStatus(plantId, isWatered)
    }

    suspend fun updateWateringDate(plantId: Int, date: String) {
        local.updateWateringDate(plantId, date)
    }
    fun getPlantsToWater(todayDate: String): List<Plant> {
        return local.getPlantsToWater(todayDate)
    }

    fun getOverduePlantsToWater(date: String): List<Plant> {
        return local.getOverduePlantToWater(date)
    }


    fun getFavouritePlants(): List<Plant> {
        return local.getFavouritePlants()
    }
}