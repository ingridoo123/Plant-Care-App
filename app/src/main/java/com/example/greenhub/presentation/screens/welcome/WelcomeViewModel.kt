package com.example.greenhub.presentation.screens.welcome


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhub.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch {
            useCases.saveOnBoardingUseCase(completed)
        }
    }

}