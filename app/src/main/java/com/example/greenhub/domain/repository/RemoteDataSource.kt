package com.example.greenhub.domain.repository

import androidx.paging.PagingData
import com.example.greenhub.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllPlants(): Flow<PagingData<Plant>>

    fun searchPlants(query: String): Flow<PagingData<Plant>>
}