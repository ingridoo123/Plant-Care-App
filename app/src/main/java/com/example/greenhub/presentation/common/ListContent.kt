package com.example.greenhub.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.greenhub.R
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.navigation.Screen
import com.example.greenhub.presentation.components.ShimmerEffect
import com.example.greenhub.ui.theme.SMALL_PADDING
import com.example.greenhub.ui.theme.descriptionColor
import com.example.greenhub.util.Constants.BASE_URL

@ExperimentalCoilApi
@Composable
fun ListContent(
    plants: List<Plant>,
    navController: NavController
) {


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = plants,
                key = {plant ->
                    plant.id
                }
            ) { item: Plant ->
                PlantItem(plant = item, navController = navController )
            }
            item(span = {GridItemSpan(2)} ) {
                Spacer(modifier = Modifier.height(70.dp).fillMaxWidth())
            }

    }
}

@ExperimentalCoilApi
@Composable
fun ListContentPaging(
    plants: LazyPagingItems<Plant>,
    navController: NavController
) {
    val result = handlePagingResult(plants = plants)

    if (result) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(
                items = plants.itemSnapshotList.items,
                key = { plant -> plant.id }
            ) { item: Plant ->
                PlantItem(plant = item, navController = navController)
            }
            item(span = {GridItemSpan(2)} ) {
                Spacer(modifier = Modifier.height(70.dp).fillMaxWidth())
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ListContent2(
    plants: List<Plant>,
    navController: NavController
) {


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = plants,
            key = {plant ->
                plant.id
            }
        ) { item: Plant ->
            PlantItem2(plant = item, navController = navController )
        }
        item(span = {GridItemSpan(2)} ) {
            Spacer(modifier = Modifier.height(70.dp).fillMaxWidth())
        }

    }
}


@Composable
fun handlePagingResult(
    plants: LazyPagingItems<Plant>
):Boolean {
    plants.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error, plants = plants)
                false
            }
            plants.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true

        }
    }
}

@ExperimentalCoilApi
@Composable
fun PlantItem(
    plant: Plant,
    navController: NavController
) {



    Box(modifier = Modifier
        .height(200.dp)
        .width(150.dp)
        .background(Color.White, shape = RoundedCornerShape(10.dp))
        .border(
            shape = RoundedCornerShape(10.dp),
            width = 1.dp,
            color = MaterialTheme.colors.descriptionColor.copy(0.1f)
        )
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable {
            navController.navigate(Screen.DetailsSearch.passPlantId(plantId = plant.id))
        },
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "$BASE_URL${plant.image}")
                    .placeholder(drawableResId = R.drawable.placeholder_grey)
                    .error(drawableResId = R.drawable.placeholder_grey)
                    .build()
                ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            )
            Text(
                text = plant.type,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                modifier = Modifier.padding(top= 8.dp),
                color = Color.Black
            )
        }
    }

}

@ExperimentalCoilApi
@Composable
fun PlantItem2(
    plant: Plant,
    navController: NavController
) {



    Box(modifier = Modifier
        .height(200.dp)
        .width(150.dp)
        .background(Color.White, shape = RoundedCornerShape(10.dp))
        .border(
            shape = RoundedCornerShape(10.dp),
            width = 1.dp,
            color = MaterialTheme.colors.descriptionColor.copy(0.1f)
        )
        .clip(shape = RoundedCornerShape(10.dp))
        .clickable {
            navController.navigate(Screen.DetailsFav.passPlantId(plantId = plant.id))
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "$BASE_URL${plant.image}")
                    .placeholder(drawableResId = R.drawable.placeholder_grey)
                    .error(drawableResId = R.drawable.placeholder_grey)
                    .build()
                ,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            )
            (if(plant.name != null) plant.name else plant.type)?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top= 8.dp),
                    color = Color.Black
                )
            }
        }
    }

}








