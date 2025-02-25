package com.example.greenhub.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.greenhub.data.local.PlantsDatabase
import com.example.greenhub.data.paging_source.PlantRemoteMediator
import com.example.greenhub.data.paging_source.SearchPlantsSource
import com.example.greenhub.data.remote.PlantApi
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow


@ExperimentalPagingApi
class RemoteDataSourceImpl(
    private val plantApi: PlantApi,
    private val plantsDatabase: PlantsDatabase
): RemoteDataSource {

    private val plantDao = plantsDatabase.plantDao()

    override fun getAllPlants(): Flow<PagingData<Plant>> {
        val pagingSourceFactory = { plantDao.getAllPlants()}
        return Pager(
            config = PagingConfig(pageSize = 3, initialLoadSize = 33),
            remoteMediator = PlantRemoteMediator(
                plantApi = plantApi,
                plantsDatabase = plantsDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override fun searchPlants(query: String): Flow<PagingData<Plant>> {
        return Pager(
            config = PagingConfig(pageSize = 3),
            pagingSourceFactory = {
                SearchPlantsSource(plantApi = plantApi, query = query)
            }
        ).flow
    }

}