package com.example.greenhub.presentation.screens.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.greenhub.R
import com.example.greenhub.presentation.components.BackArrowButton2
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.ui.theme.LightGreen
import com.example.greenhub.ui.theme.MainColor
import com.example.greenhub.ui.theme.descriptionColor
import com.example.greenhub.util.Constants.BASE_URL
import okhttp3.internal.format
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalCoilApi
@Composable
fun DetailScreen(navController: NavController, detailsViewModel: DetailsViewModel = hiltViewModel()) {

    var favPlant by remember {
        mutableStateOf(false)
    }
    val tabs = listOf("Informacje", "Pielęgnacja")

    var selectedTab by remember { mutableStateOf(tabs[0])}

    val selectedPlant by detailsViewModel.selectedPlant.collectAsState()



    LaunchedEffect(selectedPlant) {
        favPlant = selectedPlant?.favourite == true
    }


    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = "$BASE_URL${selectedPlant?.image}")
                .error(drawableResId = R.drawable.placeholder_grey)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(430.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.height(13.dp))
            BackArrowButton2(onClick = { navController.popBackStack()})
            Spacer(modifier = Modifier.height(365.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                selectedPlant?.let {
                    Text(
                        text = it.type,
                        modifier = Modifier,
                        fontWeight = FontWeight.Medium,
                        fontSize = 25.sp,
                    )
                }
                IconButton(onClick = {
                    favPlant = !favPlant
                    selectedPlant?.let {
                        detailsViewModel.updateFavouriteScreen(it.id, favPlant)
                        val number = 1
                        val date = calculateWaterDate(number)
                        detailsViewModel.updateWateringDate(it.id,date)
                    }

                }) {
                    val iconImage = if (favPlant) HeartFilled else Heart
                    Icon(
                        imageVector = iconImage,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color(0xFF13693D)
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tabs.forEach { tab ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = tab,
                            modifier = Modifier
                                .padding(bottom = 6.dp, start = 8.dp, end = 8.dp)
                                .clickable { selectedTab = tab },
                            fontWeight = if(tab == selectedTab) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize = 15.sp
                        )
                        // Podkreślenie aktywnej zakładki
                        if (tab == selectedTab) {
                            Box(
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth(0.35f)
                                    .background(Color.Black)
                            )
                        }
                    }
                }
            }
            when (selectedTab) {
                "Informacje" -> selectedPlant?.let {
                    AboutContent(plant = it) {
                        favPlant = true
                        detailsViewModel.updateFavouriteScreen(it.id, favPlant)
                        val date = calculateWaterDate(1)
                        detailsViewModel.updateWateringDate(it.id,date)
                        navController.popBackStack()
                    }
                }
                "Pielęgnacja" -> selectedPlant?.let { PlantCareContent(plant = it) }
            }

        }

    }

}

@Composable
fun AboutContent(plant: Plant, onClickFav:() -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoCard(name = plant.maintenance, icon = R.drawable.grass)
            Spacer(modifier = Modifier.width(35.dp))
            InfoCard(name = plant.sunlight, icon = R.drawable.cloudy)
            Spacer(modifier = Modifier.width(35.dp))
            InfoCard(name = plant.humidity, icon = R.drawable.water_drop)


        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = plant.description,
            color = MaterialTheme.colors.descriptionColor,
            fontSize = 13.sp,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 5.dp),
            verticalArrangement = Arrangement.Bottom
        ){
            Button(
                onClick = onClickFav,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Dodaj do ulubionych",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

}


@Composable
fun InfoCard(name: String, icon: Int){
    Box(
        modifier = Modifier
            .size(80.dp, 80.dp)
            .background(LightGreen.copy(0.3f), shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp, vertical = 7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.size(40.dp))
            Text(text = name, fontSize = 10.sp, modifier = Modifier.padding(top = 6.dp), color = MainColor, fontWeight = FontWeight.Medium)
        }

    }
}

@Composable
fun PlantCareContent(plant: Plant){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
            ) {
                InfoCard2(title = "Podlewanie", frequency = plant.wateringDays , icon = R.drawable.water, color = Color(0xff3E3BD2))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
            ) {
                InfoCard2(title = "Temperatura", frequency = "${plant.temperatureRange.first}° - ${plant.temperatureRange.second}°C", icon = R.drawable.temperature, color = Color(0xFFBE0F0F))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
            ) {
                InfoCard2(title = "Przesadzanie", frequency = plant.repot , icon = R.drawable.shovel, color = Color(0xff13693D))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
            ) {
                InfoCard2(title = "Nawóz", frequency = plant.fertilizer , icon = R.drawable.fertilizer, color = Color(0xff14BAC7))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
            ) {
                InfoCard2(title = "Wymiary", frequency = "Wysokość - ${plant.height}m,  Szerokość - ${plant.width}m" , icon = R.drawable.ruler, color = Color(0xffE9E309))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .width(350.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 8.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
            ) {
                InfoCard2(title = "Lokalizacja", frequency = plant.inHouse , icon = R.drawable.home, color = Color(0xff5e0689))
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }


}

@Composable
fun InfoCard2(title: String, frequency: String, icon: Int, color: Color) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .width(350.dp)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .background(color.copy(alpha = 0.2f), shape = RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = painterResource(id = icon), contentDescription = null,
                    modifier = if(icon == R.drawable.home) Modifier.size(25.dp) else Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(text = frequency, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colors.descriptionColor)
            }
            

        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun calculateWaterDate(days: Int): String {
    val today = LocalDate.now()
    val dateAfter = today.plusDays(days.toLong())
    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))
    return today.format(formatter)
}




public val HeartFilled: ImageVector
    get() {
        if (_HeartFilled != null) {
            return _HeartFilled!!
        }
        _HeartFilled = ImageVector.Builder(
            name = "HeartFilled",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Green),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.88f, 4.78079f)
                curveTo(14.7993f, 4.465f, 14.6748f, 4.162f, 14.51f, 3.8808f)
                curveTo(14.3518f, 3.5882f, 14.1493f, 3.3217f, 13.91f, 3.0907f)
                curveTo(13.563f, 2.7449f, 13.152f, 2.4698f, 12.7f, 2.2808f)
                curveTo(11.7902f, 1.9074f, 10.7698f, 1.9074f, 9.86f, 2.2808f)
                curveTo(9.4328f, 2.4616f, 9.0403f, 2.7154f, 8.7f, 3.0308f)
                lineTo(8.65003f, 3.09073f)
                lineTo(8.00001f, 3.74075f)
                lineTo(7.34999f, 3.09073f)
                lineTo(7.3f, 3.03079f)
                curveTo(6.9597f, 2.7154f, 6.5673f, 2.4616f, 6.14f, 2.2808f)
                curveTo(5.2302f, 1.9074f, 4.2098f, 1.9074f, 3.3f, 2.2808f)
                curveTo(2.848f, 2.4698f, 2.4371f, 2.7449f, 2.09f, 3.0907f)
                curveTo(1.8505f, 3.324f, 1.6451f, 3.59f, 1.48f, 3.8808f)
                curveTo(1.3226f, 4.1644f, 1.2016f, 4.4668f, 1.12f, 4.7808f)
                curveTo(1.0352f, 5.1072f, 0.9949f, 5.4436f, 1f, 5.7808f)
                curveTo(1.0005f, 6.0979f, 1.0408f, 6.4136f, 1.12f, 6.7207f)
                curveTo(1.2038f, 7.0308f, 1.3247f, 7.3296f, 1.48f, 7.6108f)
                curveTo(1.6477f, 7.8998f, 1.8529f, 8.1654f, 2.09f, 8.4008f)
                lineTo(8.00001f, 14.3108f)
                lineTo(13.91f, 8.40079f)
                curveTo(14.1471f, 8.1678f, 14.3492f, 7.9017f, 14.51f, 7.6108f)
                curveTo(14.6729f, 7.3321f, 14.7974f, 7.0327f, 14.88f, 6.7207f)
                curveTo(14.9592f, 6.4136f, 14.9995f, 6.0979f, 15f, 5.7808f)
                curveTo(15.0052f, 5.4436f, 14.9648f, 5.1072f, 14.88f, 4.7808f)
                close()
            }
        }.build()
        return _HeartFilled!!
    }

private var _HeartFilled: ImageVector? = null


public val Heart: ImageVector
    get() {
        if (_Heart != null) {
            return _Heart!!
        }
        _Heart = ImageVector.Builder(
            name = "Heart",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF13693D)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.88f, 4.78f)
                arcToRelative(3.489f, 3.489f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.37f, -0.9f)
                arcToRelative(3.24f, 3.24f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.6f, -0.79f)
                arcToRelative(3.78f, 3.78f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.21f, -0.81f)
                arcToRelative(3.74f, 3.74f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.84f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.16f, 0.75f)
                lineToRelative(-0.05f, 0.06f)
                lineToRelative(-0.65f, 0.65f)
                lineToRelative(-0.65f, -0.65f)
                lineToRelative(-0.05f, -0.06f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.16f, -0.75f)
                arcToRelative(3.74f, 3.74f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.84f, 0f)
                arcToRelative(3.78f, 3.78f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.21f, 0.81f)
                arcToRelative(3.55f, 3.55f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.97f, 1.69f)
                arcToRelative(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.12f, 1f)
                curveToRelative(0f, 0.317f, 0.04f, 0.633f, 0.12f, 0.94f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.36f, 0.89f)
                arcToRelative(3.8f, 3.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.61f, 0.79f)
                lineTo(8f, 14.31f)
                lineToRelative(5.91f, -5.91f)
                curveToRelative(0.237f, -0.233f, 0.44f, -0.5f, 0.6f, -0.79f)
                arcTo(3.578f, 3.578f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15f, 5.78f)
                arcToRelative(3.747f, 3.747f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.12f, -1f)
                close()
                moveToRelative(-1f, 1.63f)
                arcToRelative(2.69f, 2.69f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.69f, 1.21f)
                lineToRelative(-5.21f, 5.2f)
                lineToRelative(-5.21f, -5.2f)
                arcToRelative(2.9f, 2.9f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.44f, -0.57f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.27f, -0.65f)
                arcToRelative(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.08f, -0.69f)
                arcTo(3.36f, 3.36f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.06f, 5f)
                arcToRelative(2.8f, 2.8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.27f, -0.65f)
                curveToRelative(0.12f, -0.21f, 0.268f, -0.4f, 0.44f, -0.57f)
                arcToRelative(2.91f, 2.91f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.89f, -0.6f)
                arcToRelative(2.8f, 2.8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.08f, 0f)
                curveToRelative(0.33f, 0.137f, 0.628f, 0.338f, 0.88f, 0.59f)
                lineToRelative(1.36f, 1.37f)
                lineToRelative(1.36f, -1.37f)
                arcToRelative(2.72f, 2.72f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.88f, -0.59f)
                arcToRelative(2.8f, 2.8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.08f, 0f)
                curveToRelative(0.331f, 0.143f, 0.633f, 0.347f, 0.89f, 0.6f)
                curveToRelative(0.174f, 0.165f, 0.32f, 0.357f, 0.43f, 0.57f)
                arcToRelative(2.69f, 2.69f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.35f, 1.34f)
                arcToRelative(2.6f, 2.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.06f, 0.72f)
                horizontalLineToRelative(-0.03f)
                close()
            }
        }.build()
        return _Heart!!
    }

private var _Heart: ImageVector? = null
