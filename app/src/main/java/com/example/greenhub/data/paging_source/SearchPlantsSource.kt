package com.example.greenhub.data.paging_source;

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.greenhub.data.remote.PlantApi
import com.example.greenhub.domain.model.Plant
import javax.inject.Inject

class SearchPlantsSource @Inject constructor(
    private val plantApi: PlantApi,
    private val query: String
): PagingSource<Int, Plant>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Plant> {
        return try {
            val apiResponse = plantApi.searchPlants(name = query)
            val plants = apiResponse.plants
            if(plants.isNotEmpty()) {
                LoadResult.Page(
                    data = plants,
                    prevKey = apiResponse.prevPage,
                    nextKey = apiResponse.nextPage
                    )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Plant>): Int? {
        return state.anchorPosition //this will just return the most recently accessed index in the list
    }

}
