package com.example.greenhub.domain.use_cases.save_login

import com.example.greenhub.data.repository.Repository

class SaveLoginUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(completed: Boolean) {
        repository.saveLoginState(completed)
    }
}