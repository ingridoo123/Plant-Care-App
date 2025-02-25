package com.example.greenhub.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.greenhub.R
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.greenhub.navigation.Screen
import com.example.greenhub.ui.theme.MainColor

@Composable
fun CustomBottomBar(navController: NavController, homeScreen: Boolean = false, searchScreen: Boolean = false, favScreen: Boolean = false ) {

    var homeClicked by remember {
        mutableStateOf(homeScreen)
    }
    var searchClicked by remember {
        mutableStateOf(searchScreen)
    }
    var favClicked by remember {
        mutableStateOf(favScreen)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.BottomCenter,
    ) {
        // Prostokątny BottomBar
        BottomAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(0.5f),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                ), // Wysokość samego BottomBar
            containerColor = Color(0xFFE9E9E9), // Tło BottomBar
            contentColor = Color.Black,
            tonalElevation = 0.dp,
        ) {
            // Ikona Home
            IconButton(
                onClick = {
                    homeClicked = true
                    navController.navigate(Screen.Home.route)
                          },
                modifier = Modifier
                    .weight(1.3f)
                    .height(40.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.home_second),
                        modifier = Modifier
                            .size(25.dp)
                            .padding(bottom = 2.dp),
                        contentDescription = "Home",
                        tint = if (homeScreen) MainColor else Color.Black
                    )
                    Text(
                        text = "Home",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (homeScreen) MainColor else Color.Black
                    )
                }
            }

            // Przestrzeń na środkowy przycisk
            Spacer(modifier = Modifier.weight(1f))

            // Ikona My plants
            IconButton(
                onClick = {
                    favClicked = true
                    navController.navigate(Screen.Favourite.route)
                          },
                modifier = Modifier
                    .weight(1.3f)
                    .height(40.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.leaf),
                        modifier = Modifier
                            .size(25.dp)
                            .padding(bottom = 2.dp),
                        contentDescription = "My plants",
                        tint = if (favScreen) MainColor else Color.Black
                    )
                    Text(
                        text = "Moje rośliny",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (favScreen) MainColor else Color.Black
                    )
                }
            }
        }

        // Środkowy przycisk wystający
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(65.dp) // Rozmiar kółka
                .align(Alignment.TopCenter) // Umieszczenie nad BottomBar
                .offset(y = (-5).dp)
                .background(color = MainColor, shape = CircleShape)
        ) {
            IconButton(
                onClick = {
                    searchClicked = true
                    navController.navigate(Screen.Search.route)


                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier.size(50.dp)
                )
            }
        }
    }
}

//@Composable
//fun Test() {
//    Column(
//        modifier = Modifier.fillMaxSize().background(imageBackGroundColor),
//        verticalArrangement = Arrangement.Bottom,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        CustomBottomBar()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CustomBottomBarPreview() {
//    Test()
//}







public val Potted_plant: ImageVector
    get() {
        if (_Potted_plant != null) {
            return _Potted_plant!!
        }
        _Potted_plant = ImageVector.Builder(
            name = "Potted_plant",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(342f, 800f)
                horizontalLineToRelative(276f)
                lineToRelative(40f, -160f)
                horizontalLineTo(302f)
                close()
                moveToRelative(0f, 80f)
                quadToRelative(-28f, 0f, -49f, -17f)
                reflectiveQuadToRelative(-28f, -44f)
                lineToRelative(-45f, -179f)
                horizontalLineToRelative(520f)
                lineToRelative(-45f, 179f)
                quadToRelative(-7f, 27f, -28f, 44f)
                reflectiveQuadToRelative(-49f, 17f)
                close()
                moveTo(200f, 560f)
                horizontalLineToRelative(560f)
                verticalLineToRelative(-80f)
                horizontalLineTo(200f)
                close()
                moveToRelative(280f, -240f)
                quadToRelative(0f, -100f, 70f, -170f)
                reflectiveQuadToRelative(170f, -70f)
                quadToRelative(0f, 90f, -57f, 156f)
                reflectiveQuadToRelative(-143f, 80f)
                verticalLineToRelative(84f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(160f)
                quadToRelative(0f, 33f, -23.5f, 56.5f)
                reflectiveQuadTo(760f, 640f)
                horizontalLineTo(200f)
                quadToRelative(-33f, 0f, -56.5f, -23.5f)
                reflectiveQuadTo(120f, 560f)
                verticalLineToRelative(-160f)
                horizontalLineToRelative(320f)
                verticalLineToRelative(-84f)
                quadToRelative(-86f, -14f, -143f, -80f)
                reflectiveQuadToRelative(-57f, -156f)
                quadToRelative(100f, 0f, 170f, 70f)
                reflectiveQuadToRelative(70f, 170f)
            }
        }.build()
        return _Potted_plant!!
    }

private var _Potted_plant: ImageVector? = null










