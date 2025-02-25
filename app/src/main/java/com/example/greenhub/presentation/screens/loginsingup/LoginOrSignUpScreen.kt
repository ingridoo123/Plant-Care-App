package com.example.greenhub.presentation.screens.loginsingup

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.greenhub.R
import com.example.greenhub.navigation.Screen
import com.example.greenhub.ui.theme.DarkGreen
import com.example.greenhub.ui.theme.EXTRA_LARGE_PADDING
import com.example.greenhub.ui.theme.LightGray
import com.example.greenhub.ui.theme.MainColor

@Composable
fun LoginOrSignUpScreen(navController: NavController) {
    ChooseScreen(navController)
}

@Composable
fun ChooseScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.on_boarding1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(MainColor.copy(0.3f), MainColor.copy(0.95f), DarkGreen)
                    )
                )
                .padding(horizontal = 25.dp, vertical = EXTRA_LARGE_PADDING),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "Hi! Witamy w GreenHub    ",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Wyrusz w zieloną podróż z tą innowacyjną aplikacją mobilną do pielęgnacji roślin.",
                color = LightGray,
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = { navController.navigate(Screen.SignUp.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = DarkGreen
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "Stwórz konto",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            OutlinedButton(
                onClick = { navController.navigate(Screen.Login.route)},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 25.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp,Color.White)
            ) {
                Text(
                    text = "Zaloguj się",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

    
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginOrSignUpPreview() {
//    ChooseScreen()
//}