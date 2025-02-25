package com.example.greenhub.domain.model

import android.text.BoringLayout
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.greenhub.util.Constants.PLANT_DATABASE_TABLE
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = PLANT_DATABASE_TABLE)
data class Plant(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val type:String,
    val image:String,
    val description:String,
    val maintenance:String,
    val sunlight:String,
    val humidity:String,
    val wateringDays: String,
    val temperatureRange:Pair<Int,Int>,
    val repot: String,
    val fertilizer:String,
    val height: Double,
    val width: Double,
    val inHouse: String,
    val isSeasonal: Boolean,
    var favourite: Boolean? = null,
    var name: String? = null,
    var date: String? = null,
    val isWatered: Boolean? = null
)