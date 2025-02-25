package com.example.greenhub.presentation.screens.favourite

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.greenhub.R
import com.example.greenhub.navigation.Screen
import com.example.greenhub.presentation.common.CustomBottomBar
import com.example.greenhub.presentation.common.ListContent
import com.example.greenhub.presentation.common.ListContent2
import com.example.greenhub.ui.theme.MainColor
import com.example.greenhub.ui.theme.ShimmerMediumGray
import com.example.greenhub.ui.theme.backGroundColor
import com.example.greenhub.ui.theme.imageBackGroundColor
import okhttp3.internal.isSensitiveHeader

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavouriteScreen(navController: NavController, favouriteViewModel: FavouriteViewModel = hiltViewModel()) {

    val favouritePlants by favouriteViewModel.favouritePlants.collectAsState()

    var selectedCategory by remember { mutableStateOf("Wszystkie")}

    val filteredPlants = when(selectedCategory) {
        "Jednosezonowe" -> favouritePlants.filter { it.isSeasonal }
        "Wielosezonowe" -> favouritePlants.filter { !it.isSeasonal}
        "Tropikalne" -> favouritePlants.filter { it.humidity == "Wysoka" }
        "Europejskie" -> favouritePlants.filter { it.humidity != "Wysoka" }
        "Dom" -> favouritePlants.filter { it.inHouse.lowercase().contains("dom")}
        "Ogród" -> favouritePlants.filter { it.inHouse.lowercase().contains("ogród")}
        "Pustynne" -> favouritePlants.filter { it.humidity == "Niska" }
        else -> favouritePlants
    }

    LaunchedEffect(key1 = favouritePlants) {
        favouriteViewModel.fetchFavouritePlants()
    }




    Scaffold(
        bottomBar = {
            CustomBottomBar(navController, favScreen = true)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backGroundColor)
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Moje rośliny",
                fontSize = 27.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            if(favouritePlants.isEmpty()) {
                EmptyFavouriteScreen(navController = navController)

            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    item {
                        CategoryCard(title = "Wszystkie", isSelected = selectedCategory == "Wszystkie", 90.dp, 45.dp,onClick = { selectedCategory = "Wszystkie" })
                    }
                    item {
                        CategoryCard(title = "Wielosezonowe", isSelected = selectedCategory == "Wielosezonowe", 130.dp, 45.dp, onClick = { selectedCategory = "Wielosezonowe"})
                    }
                    item {
                        CategoryCard(title = "Jednosezonowe", isSelected = selectedCategory == "Jednosezonowe", 130.dp, 45.dp, onClick = { selectedCategory = "Jednosezonowe"})
                    }
                    item {
                        CategoryCard(title = "Europejskie", selectedCategory == "Europejskie", 110.dp, 45.dp, onClick = { selectedCategory = "Europejskie" })
                    }
                    item {
                        CategoryCard(title = "Tropikalne", isSelected = selectedCategory == "Tropikalne", 100.dp, 45.dp, onClick = { selectedCategory = "Tropikalne" })
                    }
                    item {
                        CategoryCard(title = "Pustynne", selectedCategory == "Pustynne", 80.dp, 45.dp, onClick = { selectedCategory = "Pustynne" })
                    }
                    item {
                        CategoryCard(title = "Ogród", isSelected = selectedCategory == "Ogród", 60.dp, 45.dp, onClick = { selectedCategory = "Ogród"})
                    }
                    item {
                        CategoryCard(title = "Dom", isSelected = selectedCategory == "Dom", 60.dp, 45.dp, onClick = { selectedCategory = "Dom"})
                    }

                }
            }


            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Ilość wyników: ${filteredPlants.size}",
                fontSize = 12.sp,
                color = Color.Black.copy(0.6f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))

            Log.d("FavScreen", favouritePlants.size.toString())

            ListContent2(plants = filteredPlants, navController = navController)
        }
    }

}

@Composable
fun CategoryCard(title:String, isSelected: Boolean, width: Dp, height:Dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(width, height)
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = if (isSelected) backGroundColor else imageBackGroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MainColor else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if(isSelected) MainColor else Color.Black
         )

    }
}

@Composable
fun EmptyFavouriteScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.book), contentDescription = null, modifier = Modifier.size(150.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Wygląda na to, że nie dodałeś jeszcze żadnych roślin. Kliknij poniżej, żeby znaleźć swoje ulubione rośliny.",
            fontSize = 15.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = { navController.navigate(Screen.Search.route) },
            colors = ButtonDefaults.buttonColors(containerColor = MainColor),
            modifier = Modifier
                .height(45.dp)
                .width(140.dp)
        )
        {
            Text(text = "Eksploruj", color = Color.White)

        }
    }
}