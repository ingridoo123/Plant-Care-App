package com.example.greenhub.di

import androidx.paging.ExperimentalPagingApi
import com.example.greenhub.data.local.PlantsDatabase
import com.example.greenhub.data.remote.PlantApi
import com.example.greenhub.data.repository.RemoteDataSourceImpl
import com.example.greenhub.domain.repository.RemoteDataSource
import com.example.greenhub.util.Constants.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }



    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideBorutoApi(retrofit: Retrofit): PlantApi {
        return retrofit.create(PlantApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        plantApi: PlantApi,
        plantsDatabase: PlantsDatabase
    ): RemoteDataSource {
        return RemoteDataSourceImpl(
            plantApi = plantApi,
            plantsDatabase = plantsDatabase
        )
    }
}