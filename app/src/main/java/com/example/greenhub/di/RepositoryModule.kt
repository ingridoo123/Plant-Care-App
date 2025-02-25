package com.example.greenhub.di

import android.adservices.topics.GetTopicsRequest
import android.content.Context
import com.example.greenhub.data.repository.DataStoreOperationsImpl
import com.example.greenhub.data.repository.Repository
import com.example.greenhub.domain.repository.DataStoreOperations
import com.example.greenhub.domain.use_cases.UseCases
import com.example.greenhub.domain.use_cases.check_overdue_watering_day.CheckOverdueWateringDaysUseCase
import com.example.greenhub.domain.use_cases.check_watering_day.CheckWateringDaysUseCase
import com.example.greenhub.domain.use_cases.get_all_plants.GetAllPlantsUseCase
import com.example.greenhub.domain.use_cases.get_fav_plants.GetFavouritePlantsUseCase
import com.example.greenhub.domain.use_cases.get_selected_plant.GetSelectedPlantUseCase
import com.example.greenhub.domain.use_cases.read_login.ReadLoginUseCae
import com.example.greenhub.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.greenhub.domain.use_cases.save_login.SaveLoginUseCase
import com.example.greenhub.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.greenhub.domain.use_cases.search_plants.SearchPlantsUseCase
import com.example.greenhub.domain.use_cases.update_fav_status.UpdateFavouritePlantUseCase
import com.example.greenhub.domain.use_cases.update_plant_name.UpdatePlantNameUseCase
import com.example.greenhub.domain.use_cases.update_water_status.UpdateWateringStatusUseCase
import com.example.greenhub.domain.use_cases.update_watering_date.UpdateWateringDateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context:Context): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            saveOnBoardingUseCase = SaveOnBoardingUseCase(repository),
            readOnBoardingUseCase = ReadOnBoardingUseCase(repository),
            getAllPlantsUseCase = GetAllPlantsUseCase(repository),
            searchPlantsUseCase = SearchPlantsUseCase(repository),
            getSelectedPlantUseCase = GetSelectedPlantUseCase(repository),
            updateFavouritePlantUseCase = UpdateFavouritePlantUseCase(repository),
            saveLoginUseCases = SaveLoginUseCase(repository),
            readLoginUseCase = ReadLoginUseCae(repository),
            getFavouritePlantsUseCase = GetFavouritePlantsUseCase(repository),
            updatePlantNameUseCase = UpdatePlantNameUseCase(repository),
            updateWateringDateUseCase = UpdateWateringDateUseCase(repository),
            checkWateringDaysUseCase = CheckWateringDaysUseCase(repository),
            checkOverdueWateringDaysUseCase = CheckOverdueWateringDaysUseCase(repository),
            updateWateringStatusUseCase = UpdateWateringStatusUseCase(repository)
        )
    }
}