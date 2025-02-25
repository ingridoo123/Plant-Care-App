package com.example.greenhub.data.registration

sealed class UIEvent {

    data class UserNameChanged(val username: String): UIEvent()
    data class EmailChanged(val email:String): UIEvent()
    data class PasswordChanged(val password:String): UIEvent()
    data class PasswordConfirmationChanged(val passwordConfirmation:String): UIEvent()
    data class CheckBoxClicked(val status: Boolean): UIEvent()


    object SignUpButtonClicked: UIEvent()

    object LoginButtonClicked: UIEvent()
}

