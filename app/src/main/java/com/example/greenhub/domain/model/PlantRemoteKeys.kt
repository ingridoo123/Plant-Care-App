package com.example.greenhub.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.greenhub.util.Constants.PLANT_REMOTE_KEYS_DATABASE_TABLE


@Entity(tableName = PLANT_REMOTE_KEYS_DATABASE_TABLE)
data class PlantRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?
)
