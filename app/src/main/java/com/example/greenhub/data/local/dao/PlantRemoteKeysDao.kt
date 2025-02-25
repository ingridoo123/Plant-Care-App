package com.example.greenhub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.greenhub.domain.model.PlantRemoteKeys

@Dao
interface PlantRemoteKeysDao {

    @Query("SELECT * FROM plant_remote_keys_table WHERE id = :plantId")
    suspend fun getRemoteKeys(plantId: Int): PlantRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(plantRemoteKeys: List<PlantRemoteKeys>)

    @Query("DELETE FROM plant_remote_keys_table")
    suspend fun deleteAllRemoteKeys()

}