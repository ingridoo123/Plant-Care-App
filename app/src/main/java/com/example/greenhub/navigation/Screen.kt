package com.example.greenhub.navigation

sealed class Screen(val route: String) {
    object Launch : Screen("launch_screen")
    object LoginOrSignUp: Screen("loginOrSignUp_screen")
    object Login: Screen("login_screen")
    object SignUp: Screen("signup_screen")
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object DetailsFav : Screen("detailsFav_screen/{plantId}") {
        fun passPlantId(plantId: Int): String {
            return "detailsFav_screen/$plantId"
        }
    }
    object Favourite: Screen("favourite_screen")
    object Search : Screen("search_screen")
    object DetailsSearch: Screen("detailsSearch_screen/{plantId}") {
        fun passPlantId(plantId: Int): String {
            return "detailsSearch_Screen/$plantId"
        }
    }
}
