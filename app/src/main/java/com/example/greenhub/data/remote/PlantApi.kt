package com.example.greenhub.data.remote

import com.example.greenhub.domain.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PlantApi {

    @GET("/api/plants")
    suspend fun getAllPlants(
        @Query("page") page: Int = 1
    ): ApiResponse

    @GET("/api/plants/search")
    suspend fun searchPlants(
        @Query("name") name: String
    ): ApiResponse

}