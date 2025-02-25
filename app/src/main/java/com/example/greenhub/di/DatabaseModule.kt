package com.example.greenhub.di

import android.content.Context
import androidx.room.Room
import com.example.greenhub.data.local.PlantsDatabase
import com.example.greenhub.data.repository.LocalDataSourceImpl
import com.example.greenhub.domain.repository.LocalDataSource
import com.example.greenhub.util.Constants.PLANTS_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PlantsDatabase {
        return Room.databaseBuilder(
            context,
            PlantsDatabase::class.java,
            PLANTS_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(
        database: PlantsDatabase
    ): LocalDataSource {
        return LocalDataSourceImpl(plantsDatabase = database)
    }

}