package com.example.greenhub.presentation.screens.welcome

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.greenhub.domain.model.OnBoardingPage
import com.example.greenhub.navigation.Screen
import com.example.greenhub.presentation.components.HorizontalPagerIndicator
import com.example.greenhub.ui.theme.DarkGray
import com.example.greenhub.ui.theme.EXTRA_LARGE_PADDING
import com.example.greenhub.ui.theme.LightGray
import com.example.greenhub.ui.theme.MEDIUM_PADDING
import com.example.greenhub.ui.theme.MainColor
import com.example.greenhub.ui.theme.PAGING_INDICATOR_SPACING
import com.example.greenhub.ui.theme.PAGING_INDICATOR_WIDTH
import com.example.greenhub.ui.theme.descriptionColor
import com.example.greenhub.ui.theme.fontFamilyRoboto


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WelcomeScreen(navController: NavController, welcomeViewModel: WelcomeViewModel = hiltViewModel()) {

    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )

    val pagerState = rememberPagerState(pageCount = {3})

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) {position ->
            PagerScreen(onBoardingPage = pages[position])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            pageCount = pagerState.pageCount,
            activeColor = MainColor,
            inactiveColor = LightGray,
            indicatorWidth = PAGING_INDICATOR_WIDTH,
            spacing = PAGING_INDICATOR_SPACING
        )
        FinishButton(modifier = Modifier.weight(1f), pagerState = pagerState ) {
            navController.popBackStack()
            navController.navigate(Screen.LoginOrSignUp.route)
            welcomeViewModel.saveOnBoardingState(true)

        }

    }



}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.5f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = onBoardingPage.title,
            color = DarkGray,
            fontFamily = fontFamilyRoboto,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .padding(top = MEDIUM_PADDING)  ,
            text = onBoardingPage.description,
            color = MaterialTheme.colors.descriptionColor,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
        

    }
}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    onClick:() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(visible = pagerState.currentPage == 2) {
            Button(
                modifier = Modifier.width(180.dp).height(40.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainColor,
                    contentColor = Color.White
                )
            )
            {
                Text(text = "Zaczynajmy!", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

        }
    }

}



@Composable
@Preview(showBackground = true)
fun FirstOnBoardingScreenPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(OnBoardingPage.First)
    }
}

@Composable
@Preview(showBackground = true)
fun FirstOnBoardingSecondPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(OnBoardingPage.Second)
    }
}
@Composable
@Preview(showBackground = true)
fun FirstOnBoardingThirdPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        PagerScreen(OnBoardingPage.Third)
    }
}














