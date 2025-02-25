package com.example.greenhub.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.greenhub.data.local.dao.PlantDao
import com.example.greenhub.data.local.dao.PlantRemoteKeysDao
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.model.PlantRemoteKeys

@Database(entities = [Plant::class, PlantRemoteKeys::class], version = 1)
@TypeConverters(DatabaseConverter::class)
abstract class PlantsDatabase : RoomDatabase() {

    abstract fun plantDao(): PlantDao
    abstract fun plantsRemoteKeysDao(): PlantRemoteKeysDao

}