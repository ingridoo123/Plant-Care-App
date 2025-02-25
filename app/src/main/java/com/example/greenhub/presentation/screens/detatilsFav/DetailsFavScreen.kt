package com.example.greenhub.presentation.screens.detatilsFav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.greenhub.R
import com.example.greenhub.presentation.components.BackArrowButton2
import com.example.greenhub.presentation.components.HeartIcon
import com.example.greenhub.presentation.components.VerticalDividerTextComponent
import com.example.greenhub.presentation.screens.details.Heart
import com.example.greenhub.presentation.screens.details.HeartFilled
import com.example.greenhub.presentation.screens.details.InfoCard2
import com.example.greenhub.ui.theme.MainColor
import com.example.greenhub.ui.theme.backGroundColor
import com.example.greenhub.ui.theme.descriptionColor
import com.example.greenhub.util.Constants

@ExperimentalCoilApi
@Composable
fun DetailsFavScreen(navController: NavController, detailsFavViewModel: DetailsFavViewModel = hiltViewModel()) {

    var favPlant by remember {
        mutableStateOf(true)
    }

    val selectedPlant by detailsFavViewModel.selectedPlant.collectAsState()

    var nameValue by remember {
        mutableStateOf("")
    }

    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(selectedPlant) {
        nameValue = selectedPlant?.name ?: selectedPlant?.type.orEmpty()
        favPlant = selectedPlant?.favourite == true
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xfff6f7f6))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.height(13 .dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BackArrowButton2(onClick = { navController.popBackStack() })
                IconButton(onClick = {
                    favPlant = !favPlant
                    selectedPlant?.let {
                        detailsFavViewModel.updateFavouriteStatus(it.id, favPlant)
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
            Box(
                modifier = Modifier
                    .height(325.dp)
                    .width(250.dp)
                    .shadow(
                        shape = RoundedCornerShape(10.dp),
                        elevation = 10.dp,
                        spotColor = Color.Black,
                        ambientColor = Color.Black,
                        clip = true
                    )
                    .align(Alignment.CenterHorizontally)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data = "${Constants.BASE_URL}${selectedPlant?.image}")
                        .error(drawableResId = R.drawable.placeholder_grey)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(250.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .height(325.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            selectedPlant?.let { 
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = it.type, fontSize = 14.sp)
                }
            }
            Column {
                Text(
                    text = "Twoja nazwa rośliny",
                    fontSize = 11.sp,
                    color = MaterialTheme.colors.descriptionColor.copy(0.8f),
                    modifier = Modifier.padding(top = 15.dp, start = 17.dp, bottom = 3.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 17.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(45.dp)
                            .fillMaxWidth(0.7f)
                            .shadow(
                                shape = RoundedCornerShape(15.dp),
                                elevation = 2.dp,
                                spotColor = Color.Black,
                                ambientColor = Color.Black,
                                clip = true
                            )
                            .border(
                                width = 3.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(15.dp)
                            )
                    ) {
                        TextField(
                            value = nameValue,
                            onValueChange = { nameValue = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .height(45.dp)
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .onFocusChanged {
                                    isFocused = it.isFocused
                                }
                                .clip(RoundedCornerShape(15.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xfff6f7f6),
                                textColor = Color.Black,
                                cursorColor = MainColor.copy(alpha = 0.8f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                            ),
                            textStyle = TextStyle(
                                fontSize = 13.sp
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    focusManager.clearFocus()
                                }
                            )

                        )
                    }

                    Button(onClick = {
                        selectedPlant?.let {
                            detailsFavViewModel.updatePlantName(it.id,nameValue)
                        }
                    },
                        modifier = Modifier
                            .height(35.dp)
                            .width(90.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MainColor,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(text = "Zapisz", fontSize = 14.sp)

                    }

                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Po dodaniu rośliny do ulubionych rozpocznie się cykl jej pielęgnacji.",
                color = MaterialTheme.colors.descriptionColor.copy(alpha = 0.8f),
                fontSize = 11.sp,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .align(Alignment.CenterHorizontally)
                )
            Spacer(modifier = Modifier.height(12.dp))

            selectedPlant?.let {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(350.dp)
                                .shadow(
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 2.dp,
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black,
                                    clip = true
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            FavInfoCard(title = "Podlewanie", frequency = it.wateringDays , icon = R.drawable.water, color = Color(0xff3E3BD2))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(350.dp)
                                .shadow(
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 2.dp,
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black,
                                    clip = true
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            FavInfoCard(title = "Temperatura", frequency = "${it.temperatureRange.first}° - ${it.temperatureRange.second}°C", icon = R.drawable.temperature, color = Color(0xFFBE0F0F))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(350.dp)
                                .shadow(
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 2.dp,
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black,
                                    clip = true
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            FavInfoCard(title = "Słońce", frequency = it.sunlight, icon = R.drawable.sun_fav, color = Color(0xFFFF9B17))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(350.dp)
                                .shadow(
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 2.dp,
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black,
                                    clip = true
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            FavInfoCard(title = "Przesadzanie",frequency = it.repot , icon = R.drawable.shovel, color = Color(0xff13693D))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(350.dp)
                                .shadow(
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 2.dp,
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black,
                                    clip = true
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            FavInfoCard(title = "Nawóz",frequency = it.fertilizer , icon = R.drawable.fertilizer, color = Color(0xff14BAC7))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    item {
                        Box(
                            modifier = Modifier
                                .height(55.dp)
                                .width(350.dp)
                                .shadow(
                                    shape = RoundedCornerShape(15.dp),
                                    elevation = 2.dp,
                                    spotColor = Color.Black,
                                    ambientColor = Color.Black,
                                    clip = true
                                )
                                .border(
                                    width = 3.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                )
                        ) {
                            FavInfoCard(title = "Lokalizacja" ,frequency = it.inHouse , icon = R.drawable.home, color = Color(0xff5e0689))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

        }
    }

}


@Composable
fun FavInfoCard(title: String, frequency: String, icon: Int, color: Color) {
    Box(
        modifier = Modifier
            .height(55.dp)
            .width(350.dp)
            .background(Color(0xfff6f7f6), shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .padding(horizontal = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(40.dp)
                        .background(color.copy(alpha = 0.2f), shape = RoundedCornerShape(20.dp))
                        .clip(shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = if (icon == R.drawable.home) Modifier.size(22.dp) else Modifier.size(27.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
            VerticalDivider(thickness = 1.dp, color = Color.Black)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = frequency, fontSize = 13.sp)
            }



        }

    }
}