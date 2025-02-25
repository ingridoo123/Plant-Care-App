package com.example.greenhub.data.registration

import androidx.compose.runtime.mutableStateOf

object Validator {

    var registrationUIState = mutableStateOf(RegistrationUIState())


    fun validateUsername(username: String): ValidationResult {
        return ValidationResult(
            username.length >= 3 && username.isNotEmpty() && username.lowercase() != "nigger"
        )
    }

    fun validateEmail(email: String): ValidationResult {
        return ValidationResult(
            email.contains("@") && email.isNotEmpty()
        )
    }

    fun validatePassword(password: String): ValidationResult {
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit()}
        return ValidationResult(
            password.length >= 5 && hasLetter && hasDigit
        )
    }

    fun validatePasswordConfirmation(password: String, passwordConfirmation: String): ValidationResult {
        return ValidationResult(
            password == passwordConfirmation && passwordConfirmation.isNotEmpty()
        )
    }

    fun validateCheckBox(statusValue: Boolean): ValidationResult {
        return ValidationResult(
            statusValue
        )
    }


}




data class ValidationResult(
    val status: Boolean = false
)