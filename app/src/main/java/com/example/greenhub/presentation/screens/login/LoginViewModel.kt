package com.example.greenhub.presentation.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.greenhub.data.registration.RegistrationUIState
import com.example.greenhub.data.registration.UIEvent
import com.example.greenhub.data.registration.Validator
import com.example.greenhub.domain.use_cases.UseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: UseCases
): ViewModel() {

    private val TAG = LoginViewModel::class.simpleName

    var registrationUIState = mutableStateOf(RegistrationUIState())

    var allValidation = mutableStateOf(false)

    var signUpProcess = mutableStateOf(false)
    var loginProcess = mutableStateOf(false)

    var isSignUpSuccessful = mutableStateOf(false)
    var isLoginSuccessful = mutableStateOf(false)

    var showSuccessToast = mutableStateOf(false)
    var showErrorToast = mutableStateOf(false)


    fun onEvent(event: UIEvent) {

        when(event) {
            is UIEvent.UserNameChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    username = event.username,
                    usernameError = Validator.validateUsername(event.username).status
                )
                printState()
            }
            is UIEvent.EmailChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    email = event.email,
                    emailError = Validator.validateEmail(event.email).status
                )
                printState()
            }
            is UIEvent.PasswordChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    password = event.password,
                    passwordError = Validator.validatePassword(event.password).status,
                    passwordConfirmationError = Validator.validatePasswordConfirmation(
                        event.password,
                        registrationUIState.value.passwordConfirmation
                    ).status
                )
                printState()
            }
            is UIEvent.PasswordConfirmationChanged -> {
                registrationUIState.value = registrationUIState.value.copy(
                    passwordConfirmation = event.passwordConfirmation,
                    passwordConfirmationError = Validator.validatePasswordConfirmation(
                        registrationUIState.value.password,
                        event.passwordConfirmation
                    ).status
                )
                printState()
            }
            is UIEvent.SignUpButtonClicked -> {
                checkEmptyFieldsSignUp()
                checkCheckBox()
                validateSignUpData()
                if(!registrationUIState.value.emptyFieldsError && !registrationUIState.value.checkBoxError) {
                    signUp()
                }
            }
            is UIEvent.LoginButtonClicked -> {
                checkEmptyFieldsLogIn()
                validateLogInData()
                if(!registrationUIState.value.emptyFieldsError && allValidation.value) {
                    registrationUIState.value = registrationUIState.value.copy(wrongEmailAndPassword = false)
                    logIn()
                } else {
                    registrationUIState.value = registrationUIState.value.copy(wrongEmailAndPassword = true)
                }
            }

            is UIEvent.CheckBoxClicked -> {
                registrationUIState.value = registrationUIState.value.copy(
                    checkBoxAccepted = event.status

                )
            }
        }

    }

    fun saveLoginState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.saveLoginUseCases(completed)
        }
    }



    private fun signUp() {
        Log.d(TAG, "INSIDE_signup")
        printState()
        validateSignUpData()
        createUserInFireBase(email = registrationUIState.value.email, password = registrationUIState.value.password)
    }


    private fun printState() {
        Log.d(TAG,"printState")
        Log.d(TAG,registrationUIState.value.toString())
        Log.d(TAG,allValidation.value.toString())
    }

    private fun validateSignUpData() {
        val usernameResult = Validator.validateUsername(
            username = registrationUIState.value.username
        )
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )
        val passwordConfirmationResult = Validator.validatePasswordConfirmation(
            registrationUIState.value.password,
            registrationUIState.value.passwordConfirmation
        )



        allValidation.value = usernameResult.status && emailResult.status && passwordResult.status && passwordConfirmationResult.status

        Log.d(TAG, "validation")
        Log.d(TAG, "userName = $usernameResult")
        Log.d(TAG, "email = $emailResult")
        Log.d(TAG, "password = $passwordResult")
        Log.d(TAG, "passwordConfirmation = $passwordConfirmationResult")
        Log.d(TAG, "passwordConfirmation = ${registrationUIState.value.passwordConfirmation}")
        Log.d(TAG, "password = ${registrationUIState.value.password}")
        Log.d(TAG, "status = ${allValidation.value}")

        registrationUIState.value = registrationUIState.value.copy(
            usernameError = usernameResult.status,
            emailError = emailResult.status,
            passwordError = passwordResult.status,
            passwordConfirmationError = passwordConfirmationResult.status,

        )
    }

    private fun validateLogInData() {
        val emailResult = Validator.validateEmail(
            email = registrationUIState.value.email
        )
        val passwordResult = Validator.validatePassword(
            password = registrationUIState.value.password
        )

        registrationUIState.value = registrationUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        if(emailResult.status && passwordResult.status) {
            allValidation.value = true
        }
    }

    private fun logIn() {
        Log.d(TAG, "logIn")

        val email = registrationUIState.value.email
        val password = registrationUIState.value.password

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    loginProcess.value = true
                    isLoginSuccessful.value = true
                    showSuccessToast.value = true
                } else {
                    showErrorToast.value = true
                }
            }
            .addOnFailureListener {
                showErrorToast.value = true
                registrationUIState.value = registrationUIState.value.copy(wrongEmailAndPassword = true)

            }
    }




    private fun checkEmptyFieldsSignUp() {
        val hasEmptyFields = registrationUIState.value.username.isEmpty() ||
                registrationUIState.value.email.isEmpty() ||
                registrationUIState.value.password.isEmpty() ||
                registrationUIState.value.passwordConfirmation.isEmpty()

        registrationUIState.value = registrationUIState.value.copy(
            emptyFieldsError = hasEmptyFields
        )
    }
    private fun checkEmptyFieldsLogIn() {
        val hasEmptyFields = registrationUIState.value.email.isEmpty() || registrationUIState.value.password.isEmpty()

        registrationUIState.value = registrationUIState.value.copy(
            emptyFieldsError = hasEmptyFields
        )
    }




    private fun checkCheckBox() {
        val unchecked = !registrationUIState.value.checkBoxAccepted

        registrationUIState.value = registrationUIState.value.copy(
            checkBoxError = unchecked
        )
    }

    private fun createUserInFireBase(email:String, password:String) {

        signUpProcess.value = true

        FirebaseAuth
            .getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                Log.d(TAG,"Inside_OnCompleteListener")
                Log.d(TAG,"isSucceessfull = ${it.isSuccessful}")
                signUpProcess.value = false

                if(it.isSuccessful) {
                    isSignUpSuccessful.value = true
                    showSuccessToast.value = true
                } else {
                    showErrorToast.value = true
                }

            }
            .addOnFailureListener {
                Log.d(TAG,"Inside_OnFailureListener")
                Log.d(TAG,"Exception = ${it.message}")
                Log.d(TAG,"Exception = ${it.localizedMessage}")

            }

    }




}