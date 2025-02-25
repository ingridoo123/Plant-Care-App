package com.example.greenhub.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenhub.domain.model.Plant
import java.util.concurrent.Flow

@Dao
interface PlantDao {

    @Query("SELECT * FROM plant_table ORDER BY id ASC")
    fun getAllPlants(): PagingSource<Int, Plant>

    @Query("SELECT * FROM plant_table WHERE id=:plantId")
    fun getSelectedPlant(plantId: Int): Plant

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlants(plants: List<Plant>)

    @Query("DELETE FROM plant_table")
    suspend fun deleteAllPlants()

    @Query("SELECT * FROM plant_table ORDER BY type ASC")
    fun getAllPlantsSortedByNameAscending(): PagingSource<Int, Plant>

    @Query("SELECT * FROM plant_table ORDER BY type DESC")
    fun getAllPlantsSortedByNameDescending(): PagingSource<Int, Plant>

    @Query("UPDATE plant_table SET favourite = :isFavourite WHERE id=:plantId")
    suspend fun updateFavouriteScreen(plantId: Int, isFavourite: Boolean)

    @Query("UPDATE plant_table SET name = :name WHERE id=:plantId")
    suspend fun updatePlantName(plantId: Int, name: String)

    @Query("UPDATE plant_table SET date = :date WHERE id=:plantId")
    suspend fun updateWateringDate(plantId: Int, date: String)

    @Query("UPDATE plant_table SET isWatered = :isWatered WHERE id=:plantId")
    suspend fun updateWateringStatus(plantId: Int, isWatered: Boolean)

    @Query("SELECT * FROM plant_table WHERE favourite = 1 AND date = :todayDate")
    fun getPlantsToWater(todayDate: String): List<Plant>

    @Query("SELECT * FROM plant_table WHERE favourite AND date < :date")
    fun getOverduePlantsToWater(date: String): List<Plant>


    @Query("SELECT * FROM plant_table WHERE favourite = 1 ORDER BY id ASC")
    fun getFavouritePlants(): List<Plant>

}