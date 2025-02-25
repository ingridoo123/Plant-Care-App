package com.example.greenhub.domain.model

import androidx.annotation.DrawableRes
import com.example.greenhub.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First: OnBoardingPage(
        image = R.drawable.welcome_back,
        title = "Witaj",
        description = "Dzięki tej aplikacji twoje rośliny będą zdrowe i piękne jak nigdy wcześniej"
    )
    object Second: OnBoardingPage(
        image = R.drawable.app2,
        title = " O Aplikacji",
        description = "GreenHub pomoże Ci w codziennej pielęgnacji roślin. Od przypomnień o podlewaniu, po wskazówki dotyczące nawożenia"
    )
    object Third: OnBoardingPage(
        image = R.drawable.saveplants,
        title = "Do dzieła!",
        description = " Jesteśmy gotowi, by pomóc Ci dbać o zielone życie wokół Ciebie. Rozpocznij swoją przygodę z pielęgnacją roślin już teraz!"
    )
}