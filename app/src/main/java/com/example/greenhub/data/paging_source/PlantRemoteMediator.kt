package com.example.greenhub.data.paging_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.greenhub.data.local.PlantsDatabase
import com.example.greenhub.data.remote.PlantApi
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.model.PlantRemoteKeys
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@ExperimentalPagingApi
class PlantRemoteMediator(
    private val plantApi: PlantApi,
    private val plantsDatabase: PlantsDatabase
): RemoteMediator<Int, Plant>() {

    private val plantDao = plantsDatabase.plantDao()
    private val plantsRemoteKeysDao = plantsDatabase.plantsRemoteKeysDao()



    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        val lastUpdated = plantsRemoteKeysDao.getRemoteKeys(plantId = 1)?.lastUpdated ?: 0L
        val cacheTimeout = 1440

        Log.d("RemoteMediator", "Current Time: ${parseMillis(currentTime)}")
        Log.d("RemoteMediator", "Last updated Time: ${parseMillis(lastUpdated)}")

        val diffInMinutes = (currentTime - lastUpdated) / 1000 / 60

        return if(diffInMinutes.toInt() <= cacheTimeout) {
            Log.d("RemoteMediator", "UP TO DATE!")
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            Log.d("RemoteMediator", "REFRESH!")
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    override suspend fun load(loadType: LoadType, state: PagingState<Int, Plant>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = plantApi.getAllPlants(page = page)
            if (response.plants.isNotEmpty()) {
                plantsDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        plantDao.deleteAllPlants()
                        plantsRemoteKeysDao.deleteAllRemoteKeys()
                    }
                    val prevPage = response.prevPage
                    val nextPage = response.nextPage
                    val keys = response.plants.map { hero ->
                        PlantRemoteKeys(
                            id = hero.id,
                            prevPage = prevPage,
                            nextPage = nextPage,
                            lastUpdated = response.lastUpdated
                        )
                    }
                    plantsRemoteKeysDao.addAllRemoteKeys(plantRemoteKeys = keys)
                    plantDao.addPlants(plants = response.plants)
                }
            }
            MediatorResult.Success(endOfPaginationReached = response.nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Plant>): PlantRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                plantsRemoteKeysDao.getRemoteKeys(plantId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Plant>
    ): PlantRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { plant ->
                plantsRemoteKeysDao.getRemoteKeys(plantId = plant.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Plant>
    ): PlantRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { plant ->
                plantsRemoteKeysDao.getRemoteKeys(plantId = plant.id)
            }
    }

        private fun parseMillis(millis: Long): String {
        val date = Date(millis)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.ROOT)
        return format.format(date)
    }


}