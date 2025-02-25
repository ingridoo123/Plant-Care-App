package com.example.greenhub.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.greenhub.presentation.screens.details.DetailScreen
import com.example.greenhub.presentation.screens.detatilsFav.DetailsFavScreen
import com.example.greenhub.presentation.screens.favourite.FavouriteScreen
import com.example.greenhub.presentation.screens.home.HomeScreen
import com.example.greenhub.presentation.screens.launch.LaunchScreen
import com.example.greenhub.presentation.screens.login.LoginScreen
import com.example.greenhub.presentation.screens.loginsingup.LoginOrSignUpScreen
import com.example.greenhub.presentation.screens.search.SearchScreen
import com.example.greenhub.presentation.screens.signup.SignUpScreen
import com.example.greenhub.presentation.screens.welcome.WelcomeScreen


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Launch.route
    ) {
        composable(route = Screen.Launch.route) {
            LaunchScreen(navController = navController)

        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)

        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(navController = navController)

        }
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.LoginOrSignUp.route){
            LoginOrSignUpScreen(navController = navController)

        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)

        }
        composable(route = Screen.Favourite.route) {
            FavouriteScreen(navController = navController)
        }
        composable(route = Screen.DetailsFav.route, arguments = listOf(navArgument("plantId") {
            type = NavType.IntType
        })) {
            DetailsFavScreen(navController = navController)

        }
        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController)

        }
        composable(route = Screen.DetailsSearch.route, arguments = listOf(navArgument("plantId") {
            type = NavType.IntType
        })) {
            DetailScreen(navController = navController)

        }

    }
}