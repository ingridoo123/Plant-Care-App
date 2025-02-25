package com.example.greenhub.data.local

import androidx.room.TypeConverter
import java.lang.StringBuilder

class DatabaseConverter {

    private val separator = ","

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        val stringBuilder = StringBuilder()
        for (item in list) {
            stringBuilder.append(item).append(separator)
        }

        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }

    @TypeConverter
    fun fromTemperatureRange(range: Pair<Int,Int>): String {
        return "${range.first},${range.second}"
    }

    @TypeConverter
    fun toTemperatureRange(range:String): Pair<Int,Int> {
        val temperature = range.split(",")
        return Pair(temperature[0].toInt(),temperature[1].toInt())
    }



}