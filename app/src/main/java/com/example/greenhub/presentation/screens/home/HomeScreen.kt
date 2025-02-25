@file:OptIn(FlowPreview::class)

package com.example.greenhub.presentation.screens.home

import android.annotation.SuppressLint
import android.hardware.lights.Light
import android.os.Build
import android.util.Log
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.greenhub.R
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.presentation.common.CustomBottomBar
import com.example.greenhub.ui.theme.LightGreen
import com.example.greenhub.ui.theme.MainColor
import com.example.greenhub.ui.theme.backGroundColor
import com.example.greenhub.ui.theme.descriptionColor
import com.example.greenhub.util.Constants
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.internal.format
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel = hiltViewModel() ) {

    val currentDateTime = remember { getCurrentTime() }

    val currentWeek = remember { getCurrentWeek() }

    val today = remember { LocalDate.now() }



   // val (selectedDay,setSelectedDay) = remember { mutableStateOf<String?>(today.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl")))) }

    val selectedDay by homeViewModel.selectedDay.collectAsState()

    val wateringDayPlants by homeViewModel.wateringDayPlants.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()



    Scaffold(
        bottomBar = {
            CustomBottomBar(navController, homeScreen = true)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backGroundColor)
                .padding(15.dp),
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Lista zadań",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = currentDateTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.descriptionColor.copy(alpha = 0.5f),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                currentWeek.forEach { day ->
                    val isToday = day.startsWith(today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("pl")))
                    val isSelected = selectedDay == day && !isToday
                    Log.d("TODAY","${selectedDay}")
                    DateCard(day = day, isToday = isToday, isSelected = isSelected, onClick = {
                        homeViewModel.setSelectedDay(day)
                    })
                }
            }
            Spacer(modifier = Modifier.height(10.dp))


            Log.d("Today", "$selectedDay selected day")
            if (isLoading){
                CircularProgressIndicator(color = backGroundColor)
            } else {
                selectedDay?.let { selected ->
                    val selectedDate = LocalDate.parse(selected, DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl")))

                    Log.d("Today",wateringDayPlants.map { it.type }.toString())
                    when {
                        selectedDate.isBefore(today) -> {
                            DisplayPastDayStatus(wateringDayPlants = wateringDayPlants)
                        }

                        selectedDate.isEqual(today) -> {
                            DisplayTodayStatus(wateringDayPlants = wateringDayPlants, onClick = { plantId, isWatered ->
                                homeViewModel.updateWateringStatus(plantId, isWatered)
                            })
                        }

                        selectedDate.isAfter(today) -> {
                            DisplayFutureTasks(wateringDayPlants = wateringDayPlants)
                        }
                    }
                }
            }






        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentTime(): String {
    val currentDate = LocalDateTime.now()
    val nextDate = currentDate.plusDays(7)
    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))
    return currentDate.format(formatter).replaceFirstChar { it.uppercaseChar() }
}


@RequiresApi(Build.VERSION_CODES.O)
fun getCurrentWeek(): List<String> {
    val today = LocalDate.now()
    val nextToday = today.plusDays(7)
    val firstDayOfWeek = today.with(DayOfWeek.MONDAY)
    val formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy", Locale("pl"))
    return (0..6).map { dayOffset ->
        val date = firstDayOfWeek.plusDays(dayOffset.toLong())
        val day = date.format(formatter)
        day
    }
}

@Composable
fun DisplayPastDayStatus(wateringDayPlants: List<Plant>) {

    Spacer(modifier = Modifier.height(15.dp))
    when {
        wateringDayPlants.isEmpty() -> {
            Text(
                text = "Nie było żadnych roślin do podlania w ten dzień",
                fontSize = 16.sp,
                color = MaterialTheme.colors.descriptionColor
            )
        }
        wateringDayPlants.all { it.isWatered == true } -> {
            Text(
                text = "W tym dniu wykonano wszystkie zadania związane z roślinami",
                fontSize = 16.sp,
                color = MainColor
            )
        }
        else -> {
            Text(
                text = buildString {
                    append("W tym dniu nie wykonano następujących zadań:\n")
                    wateringDayPlants.filter { it.isWatered != true }.forEach { plant ->
                        append("Nie podlano ${plant.type}\n")
                    }
                },
                fontSize = 16.sp,
                color = Color.Red
            )
        }
    }
}


@Composable
fun DisplayTodayStatus(wateringDayPlants: List<Plant>, onClick: (Int, Boolean) -> Unit) {

    val totalTasks = wateringDayPlants.size
    val completedTasks = wateringDayPlants.count { it.isWatered == true }
    val progressPercentage = if(totalTasks > 0) completedTasks.toFloat() / totalTasks else 0f
    val progressText = if(progressPercentage == 0f) "0% wykonane" else "${(progressPercentage * 100).toInt()}% wykonane"



    if(wateringDayPlants.isNotEmpty()) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
        ) {
            Text(
                text = "Dzisiejszy postęp",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = MainColor,
                modifier = Modifier
                    .padding(bottom = 23.dp, start = 10.dp)
                    .align(Alignment.Start)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(progressPercentage)
                    .padding(bottom = 5.dp)
                    .height(15.dp)
            ) {
                Text(
                    text = progressText,
                    fontSize = 12.sp,
                    color = MainColor,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 10.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(7.dp))
                    .background(MainColor.copy(0.4f))
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(progressPercentage)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MainColor)
                )
            }
            Text(
                text = "Pozostałe zadania: ${totalTasks-completedTasks}",
                fontSize = 12.sp,
                color = MaterialTheme.colors.descriptionColor.copy(alpha = 0.5f),
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 5.dp, end = 10.dp)
            )
        }


        Column(
            modifier = Modifier
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            wateringDayPlants.forEach { plant ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 13.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TaskCard(plant = plant, onClick = onClick)
                }
            }

        }

    } else {
        Text(
            text = "Nie ma dzisiaj roślin do podlania" ,
            fontSize = 16.sp,
            color = MaterialTheme.colors.descriptionColor
        )
    }
    val allWatered = wateringDayPlants.all { it.isWatered == true }
//    Spacer(modifier = Modifier.height(20.dp))
//    if (allWatered && wateringDayPlants.isNotEmpty()) {
//        Text(
//            text = "Wykonano dzisiejsze zadania!!",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Green
//        )
//    } else if(!allWatered && wateringDayPlants.isNotEmpty()) {
//        Text(
//            text = "Rośliny jeszcze do podlania!!!",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Red
//        )
//    } else {
//        Text(
//            text = "Rośliny jeszcze do podlania!!!",
//            fontSize = 18.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.Transparent
//        )
//    }
}

@Composable
fun DisplayFutureTasks(wateringDayPlants: List<Plant>) {
    Spacer(modifier = Modifier.height(15.dp))
    if (wateringDayPlants.isNotEmpty()) {
        Text(
            text = "Rośliny do podlania w ten dzień:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MainColor,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        wateringDayPlants.forEach { plant ->
            Text(text = plant.type, fontSize = 16.sp, color = Color.Black)
        }
    } else {
        Text(
            text = "Aktualnie brak roślin do podlania w ten dzień.",
            fontSize = 16.sp,
            color = MaterialTheme.colors.descriptionColor
        )
    }
}

@Composable
fun DateCard(day: String, isToday: Boolean, isSelected: Boolean, onClick:() -> Unit) {

    val backGroundColor = when {
        isToday -> MainColor
        isSelected -> MainColor.copy(alpha = 0.3f)
        else -> Color.White
    }
    val height = when {
        isToday -> 80.dp
        isSelected -> 80.dp
        else -> 70.dp
    }
    val padding = if(isToday || isSelected) 0.dp else 5.dp

    val textColor = when {
        isToday -> Color.White
        isSelected -> MainColor
        else -> Color.Black
    }
    val width = if(isSelected) 2.dp else 1.dp
    val borderColor = if(isSelected) MainColor else MaterialTheme.colors.descriptionColor.copy(alpha = 0.1f)

    Box(
        modifier = Modifier
            .padding(vertical = padding)
            .height(height)
            .width(50.dp)
            .background(backGroundColor, shape = RoundedCornerShape(15.dp))
            .border(
                width = width,
                shape = RoundedCornerShape(15.dp),
                color = borderColor
            )
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable { onClick() }
    ){
        Column(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = day.substring(0, 3).replaceFirstChar { it.uppercaseChar() }, fontSize = 14.sp, modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.CenterHorizontally),
                color = textColor
            )
            val regex = Regex("\\b\\d{1,2}\\b")
            val dayNumber =  regex.findAll(day).map { it.value.toInt() }.firstOrNull()
            Text(text = dayNumber.toString(), fontWeight = FontWeight.Medium, color = textColor)


        }

    }
}

@Composable
fun TaskCard(plant: Plant, onClick: (Int, Boolean) -> Unit) {

    val color = if(plant.isWatered == true) MainColor.copy(0.9f) else MainColor.copy(alpha = 0.7f)


    Box(
        modifier = Modifier
            .height(80.dp)
            .width(375.dp)
            .clip(RoundedCornerShape(20.dp))
            .border(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colors.descriptionColor.copy(0.1f),
                width = 1.dp
            )

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "${Constants.BASE_URL}${plant.image}")
                    .error(drawableResId = R.drawable.placeholder_grey)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.22f)
                    .fillMaxHeight()
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(vertical = 10.dp, horizontal = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = if(plant.name != null) plant.name!! else plant.type,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 3.dp),
                        color = Color.White
                    )
                    Text(text = "Podlewanie",fontSize = 15.sp, color = Color.White)
                }
                Checkbox(checked = plant.isWatered ?: false, onCheckedChange = { isChecked ->
                    onClick(plant.id, isChecked) },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = MainColor.copy(0.9f),
                        checkedColor = Color.White,
                        uncheckedColor = Color.White
                        ),
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 10.dp)
                )

            }
        }

    }
}