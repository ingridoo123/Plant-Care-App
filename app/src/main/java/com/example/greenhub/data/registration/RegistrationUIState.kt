package com.example.greenhub.data.registration

data class RegistrationUIState(
    var username: String = "",
    var email: String = "",
    var password: String = "",
    var passwordConfirmation: String = "",
    var checkBoxAccepted: Boolean = false,

    var usernameError: Boolean = false,
    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var passwordConfirmationError: Boolean = false,
    var checkBoxError: Boolean = false,

    var emptyFieldsError: Boolean = false,
    var wrongEmailAndPassword: Boolean = false
)