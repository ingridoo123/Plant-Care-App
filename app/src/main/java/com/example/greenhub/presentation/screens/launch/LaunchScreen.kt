package com.example.greenhub.presentation.screens.launch

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.greenhub.R
import com.example.greenhub.navigation.Screen
import com.example.greenhub.ui.theme.DarkGreen
import com.example.greenhub.ui.theme.MainColor
import com.example.greenhub.ui.theme.fontFamilyRoboto

@Composable
fun LaunchScreen(navController: NavController,launchViewModel: LaunchViewModel = hiltViewModel()) {

    val onBoardingCompleted by launchViewModel.onBoardingCompleted.collectAsState()
    val loginCompleted by launchViewModel.loginCompleted.collectAsState()

    LaunchedEffect(key1 = true) {

        kotlinx.coroutines.delay(1500)
        Log.d("Stany","onBoarding:${onBoardingCompleted}")
        Log.d("Stany","login:${loginCompleted}")

        navController.popBackStack()
        if(onBoardingCompleted && loginCompleted) {
            navController.navigate(Screen.Home.route)
        } else if(onBoardingCompleted) {
            navController.navigate(Screen.LoginOrSignUp.route)
        } else {
            navController.navigate(Screen.Welcome.route)
        }
    }
    Launch()
}


@Composable
fun Launch() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(MainColor, DarkGreen))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.plant_2),
                contentDescription = null
            )
            Text(
                text = "GREENHUB",
                fontFamily = fontFamilyRoboto,
                fontWeight = FontWeight.SemiBold,
                fontSize = 32.sp,
                color = Color.White
                )

        }

    }
}


