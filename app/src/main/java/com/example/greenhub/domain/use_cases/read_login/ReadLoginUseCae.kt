package com.example.greenhub.domain.use_cases.read_login

import com.example.greenhub.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadLoginUseCae(
    private val repository: Repository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readLoginState()
    }
}