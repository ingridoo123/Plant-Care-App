@file:OptIn(FlowPreview::class)

package com.example.greenhub.presentation.screens.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.domain.use_cases.UseCases
import com.example.greenhub.presentation.common.EmptyScreenPreview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    private val today = LocalDate.now()
    private val dateFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))

    private val _selectedDay = MutableStateFlow<String?>(today.format(dateFormatter))
    val selectedDay = _selectedDay.asStateFlow()

    private val _wateringDayPlants = MutableStateFlow<List<Plant>>(emptyList())
    val wateringDayPlants = _wateringDayPlants

    private val _isLoading = MutableStateFlow(false) // Dodajemy wskaźnik ładowania
    val isLoading = _isLoading.asStateFlow()

    init {
        checkAndUpdateWateringDay(_selectedDay.value ?: today.format(dateFormatter))
    }

    fun setSelectedDay(day: String) {
        _selectedDay.value = day
        checkAndUpdateWateringDay(day)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkAndUpdateWateringDay(date: String) {


        viewModelScope.launch(Dispatchers.IO) {

            _isLoading.value = true

            val todayDate = LocalDate.now()
            Log.d("CZAS",todayDate.toString())
            val overduePlantsToWater = useCases.checkOverdueWateringDaysUseCase(date)
            val currentWeekStart = todayDate.with(DayOfWeek.MONDAY)
            val currentWeekEnd = todayDate.with(DayOfWeek.SUNDAY)

            for (plant in overduePlantsToWater) {
                var wateringDate = LocalDate.parse(
                    plant.date,
                    DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))
                )
                var running = true
                if (wateringDate.isBefore(todayDate) && wateringDate !in currentWeekStart..currentWeekEnd) {
                    while (running) {
                        val nextDays = plant.wateringDays.filter { it.isDigit() }.toInt()
                        val newWateringDate = wateringDate.plusDays(nextDays.toLong())
                        useCases.updateWateringDateUseCase(
                            plant.id,
                            newWateringDate.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))).toString()
                        )
                        wateringDate = newWateringDate
                        if (newWateringDate.isAfter(todayDate) || newWateringDate.isEqual(todayDate)) {
                            running = false
                        }
                    }
                }
                else if(wateringDate.isBefore(todayDate) && wateringDate.plusDays(plant.wateringDays.filter { it.isDigit() }.toInt().toLong()) in currentWeekStart..currentWeekEnd) {
                    while (running) {
                        val nextDays = plant.wateringDays.filter { it.isDigit() }.toInt()
                        val newWateringDate = wateringDate.plusDays(nextDays.toLong())
                        useCases.updateWateringDateUseCase(
                            plant.id,
                            newWateringDate.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))).toString()
                        )
                        wateringDate = newWateringDate
                        if (newWateringDate.isAfter(todayDate) || newWateringDate.isEqual(todayDate)) {
                            running = false
                        }
                    }
                }
            }

            val plantToWater = useCases.checkWateringDaysUseCase(date)

            _wateringDayPlants.value = plantToWater
            _isLoading.value = false

        }
    }



    fun updateWateringStatus(plantId: Int, isWatered: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.updateWateringStatusUseCase(plantId, isWatered)
            _wateringDayPlants.update {
                it.map { plant ->
                    if(plant.id == plantId) {
                        plant.copy(isWatered = isWatered)
                    } else {
                        plant
                    }
                }
            }
        }
    }
}