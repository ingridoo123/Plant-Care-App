package com.example.greenhub.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.greenhub.R
import com.example.greenhub.data.registration.UIEvent
import com.example.greenhub.navigation.Screen
import com.example.greenhub.presentation.components.BackArrowButton
import com.example.greenhub.presentation.components.ClickableSignUpTextComponent
import com.example.greenhub.presentation.components.DividerTextComponent
import com.example.greenhub.presentation.components.GoogleButton
import com.example.greenhub.presentation.components.MyTextFieldEmail2
import com.example.greenhub.presentation.components.MyTextFieldPasswordLogin
import com.example.greenhub.ui.theme.DarkGreen
import com.example.greenhub.ui.theme.LARGE_PADDING
import com.example.greenhub.ui.theme.SMALL_PADDING
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

@Composable
fun LoginScreen(navController: NavController) {
    LoginCard(navController = navController)

}

@Composable
fun LoginCard(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {
    val hazeState = remember { HazeState() }

    val context = LocalContext.current

    val isLoginSuccessful = loginViewModel.isLoginSuccessful.value
    val successToast = loginViewModel.showSuccessToast.value
    val errorToast = loginViewModel.showErrorToast.value
    val loginProcess = loginViewModel.loginProcess.value

    val uiState = loginViewModel.registrationUIState.value

    LaunchedEffect(isLoginSuccessful) {
        if(isLoginSuccessful) {

            loginViewModel.saveLoginState(true)
            navController.navigate(Screen.Search.route) {
                navController.popBackStack()
            }
        }
    }

    LaunchedEffect(successToast) {
        if(successToast) {
            Toast.makeText(context,"Pomyślnie zalogowano!",Toast.LENGTH_SHORT).show()
            loginViewModel.showSuccessToast.value = false
        }
    }
    LaunchedEffect(errorToast) {
        if(errorToast) {
            Toast.makeText(context,"Nie udało się zalogować",Toast.LENGTH_SHORT).show()
            loginViewModel.showErrorToast.value = false
        }

    }






    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Tło obrazu z efektem haze
        Box(
            modifier = Modifier
                .fillMaxSize()
                .haze(
                    state = hazeState,
                    backgroundColor = MaterialTheme.colors.background,
                    tint = Color.Black.copy(0.1f),
                    blurRadius = 20.dp
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.on_boarding1),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp)
        ) {
            BackArrowButton({navController.navigate(Screen.LoginOrSignUp.route)}, modifier = Modifier
                .hazeChild(hazeState, shape = CircleShape)
                .size(45.dp))

            Spacer(modifier = Modifier.height(25.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .hazeChild(hazeState, shape = RoundedCornerShape(20.dp))
                    .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                    .border(1.dp, Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(LARGE_PADDING),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.plant_2),
                            contentDescription = null,
                            modifier = Modifier.size(60.dp)
                        )
                        Text(
                            text = "Witamy z powrotem!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp
                        )
                        Text(
                            text = "Twój przewdnik po roślinach",
                            fontSize = 15.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = SMALL_PADDING)
                        )
                        Spacer(modifier = Modifier.height(7.dp))

                        if(uiState.emptyFieldsError) {
                            Text(
                                text = "Nie może być pustych miejsc",
                                color = Color.Red,
                                fontSize = 12.sp,
                            )
                        } else if (uiState.wrongEmailAndPassword){
                            Text(text = "Błędny email lub hasło", color = Color.Red, fontSize = 12.sp)
                        } else {
                            Text(text = "Nie może być pustych miejsc", color = Color.Transparent, fontSize = 12.sp )
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Email",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(bottom = 10.dp),
                            color = Color.White.copy(0.9f)
                        )
                        MyTextFieldEmail2(onTextSelected =
                        {
                            loginViewModel.onEvent(UIEvent.EmailChanged(it))
                        })
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Hasło",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(bottom = 10.dp),
                            color = Color.White.copy(0.9f)
                        )
                        MyTextFieldPasswordLogin("Wprowadź hasło", onTextSelected = {
                            loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                        })


                        Text(text = "Zapomniałeś hasła?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.End))
                        Spacer(modifier = Modifier.height(20.dp))


                        Button(
                            onClick = {
                                      loginViewModel.onEvent(UIEvent.LoginButtonClicked)
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .height(40.dp)
                                .align(Alignment.CenterHorizontally),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.White.copy(0.9f),
                                contentColor = DarkGreen
                            ),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(text = "Zaloguj się")
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        DividerTextComponent()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            GoogleButton(text = "Zaloguj się z Google", loadingText = "Logowanie...") {}
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            ClickableSignUpTextComponent(onTextSelected = {
                                navController.popBackStack()
                                navController.navigate(Screen.SignUp.route)
                            })
                        }
                    }
                }

            }
        }


    }
}