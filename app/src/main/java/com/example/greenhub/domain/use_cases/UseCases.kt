package com.example.greenhub.domain.use_cases

import com.example.greenhub.domain.use_cases.check_overdue_watering_day.CheckOverdueWateringDaysUseCase
import com.example.greenhub.domain.use_cases.check_watering_day.CheckWateringDaysUseCase
import com.example.greenhub.domain.use_cases.get_all_plants.GetAllPlantsUseCase
import com.example.greenhub.domain.use_cases.get_fav_plants.GetFavouritePlantsUseCase
import com.example.greenhub.domain.use_cases.get_selected_plant.GetSelectedPlantUseCase
import com.example.greenhub.domain.use_cases.update_fav_status.UpdateFavouritePlantUseCase
import com.example.greenhub.domain.use_cases.read_login.ReadLoginUseCae
import com.example.greenhub.domain.use_cases.read_onboarding.ReadOnBoardingUseCase
import com.example.greenhub.domain.use_cases.save_login.SaveLoginUseCase
import com.example.greenhub.domain.use_cases.save_onboarding.SaveOnBoardingUseCase
import com.example.greenhub.domain.use_cases.search_plants.SearchPlantsUseCase
import com.example.greenhub.domain.use_cases.update_plant_name.UpdatePlantNameUseCase
import com.example.greenhub.domain.use_cases.update_water_status.UpdateWateringStatusUseCase
import com.example.greenhub.domain.use_cases.update_watering_date.UpdateWateringDateUseCase

data class UseCases(
    val saveOnBoardingUseCase: SaveOnBoardingUseCase,
    val readOnBoardingUseCase: ReadOnBoardingUseCase,
    val getAllPlantsUseCase: GetAllPlantsUseCase,
    val searchPlantsUseCase: SearchPlantsUseCase,
    val getSelectedPlantUseCase: GetSelectedPlantUseCase,
    val updateFavouritePlantUseCase: UpdateFavouritePlantUseCase,
    val saveLoginUseCases: SaveLoginUseCase,
    val readLoginUseCase: ReadLoginUseCae,
    val getFavouritePlantsUseCase: GetFavouritePlantsUseCase,
    val updatePlantNameUseCase: UpdatePlantNameUseCase,
    val updateWateringDateUseCase: UpdateWateringDateUseCase,
    val checkWateringDaysUseCase: CheckWateringDaysUseCase,
    val checkOverdueWateringDaysUseCase: CheckOverdueWateringDaysUseCase,
    val updateWateringStatusUseCase: UpdateWateringStatusUseCase
)
