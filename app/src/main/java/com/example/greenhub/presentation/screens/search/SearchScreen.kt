package com.example.greenhub.presentation.screens.search

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.example.greenhub.R
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.presentation.common.CustomBottomBar
import com.example.greenhub.presentation.common.ListContent
import com.example.greenhub.presentation.common.ListContentPaging
import com.example.greenhub.ui.theme.backGroundColor
import com.example.greenhub.ui.theme.descriptionColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalCoilApi
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = hiltViewModel()) {

    val allPlants = searchViewModel.getAllPlants.collectAsLazyPagingItems()
    val searchQuery by searchViewModel.searchQuery
    val searchedPlants = searchViewModel.searchedPlants.collectAsLazyPagingItems()

    var sortedPlants = searchViewModel.sortedPlants


    var isFocused by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current



    Scaffold(
        bottomBar = {
            CustomBottomBar(navController, searchScreen = true)
      }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backGroundColor)
                .padding(15.dp),
        ) {
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Szukaj",
                fontSize = 27.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchViewModel.updateSearchQuery(it) },
                    singleLine = true,
                    textStyle = TextStyle(
                        color = MaterialTheme.colors.descriptionColor.copy(0.7f),
                        fontSize = 13.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .background(Color.White, shape = RoundedCornerShape(13.dp))
                        .height(40.dp)
                        .border(
                            shape = RoundedCornerShape(13.dp),
                            width = 1.dp,
                            color = MaterialTheme.colors.descriptionColor.copy(0.2f)
                        )
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            isFocused = it.isFocused
                        },
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colors.descriptionColor.copy(0.7f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(modifier = Modifier.fillMaxWidth(0.85f)) {
                                if (searchQuery.isEmpty()) {
                                    Text(
                                        text = "Czego szukasz?",
                                        color = MaterialTheme.colors.descriptionColor.copy(0.7f),
                                        fontSize = 13.sp
                                    )
                                }
                                innerTextField()
                            }
                            IconButton(
                                onClick = {
                                    if (searchQuery.isNotEmpty()) {
                                        searchViewModel.updateSearchQuery("")
                                    } else {
                                        focusManager.clearFocus()
                                    }
                                },
                                modifier = Modifier.alpha(if (isFocused) 0.7f else 0f)
                            ) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = null)
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            searchViewModel.searchPlants(searchQuery)
                            focusManager.clearFocus()
                        }
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = null,
                    modifier = Modifier
                        .size(38.dp)
                        .clickable {
                            showFilterDialog = true
                        }
                )
            }
            if (showFilterDialog) {
                AlertDialog(
                    onDismissRequest = { showFilterDialog = false },
                    title = { Text(text = "Opcje sortowania") },
                    text = {
                        Column {
                            Text(
                                text = "Sortuj od A-Z",
                                modifier = Modifier
                                    .clickable {
                                        searchViewModel.sortedPlants =
                                            if (searchQuery.isNotEmpty()) searchedPlants.itemSnapshotList.items.sortedBy { it.type } else allPlants.itemSnapshotList.items.sortedBy { it.type }
                                        showFilterDialog = false
                                        Log.d("Search", "clicked A-Z")
                                    }
                                    .padding(vertical = 8.dp)
                            )
                            Text(
                                text = "Sortuj od Z-A",
                                modifier = Modifier
                                    .clickable {
                                        searchViewModel.sortedPlants =
                                            if (searchQuery.isNotEmpty()) searchedPlants.itemSnapshotList.items.sortedByDescending { it.type } else allPlants.itemSnapshotList.items.sortedByDescending { it.type }
                                        showFilterDialog = false
                                        Log.d("Search", "clicked Z-A")
                                    }
                                    .padding(vertical = 8.dp)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showFilterDialog = false }) {
                            Text("Zamknij")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            sortedPlants = searchViewModel.sortedPlants
            if (sortedPlants != null) {
                ListContent(plants = sortedPlants!!, navController = navController)
                Log.d("Search", "inside first listcontent")
            } else {
                ListContentPaging(
                    plants = if (searchQuery.isNotEmpty()) searchedPlants else allPlants,
                    navController = navController
                )
                Log.d("Search", "inside second listcontent")
            }
        }
    }

}






