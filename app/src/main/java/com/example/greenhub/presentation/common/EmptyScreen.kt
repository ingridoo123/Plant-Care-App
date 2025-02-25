package com.example.greenhub.presentation.common

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.greenhub.R
import com.example.greenhub.domain.model.Plant
import com.example.greenhub.ui.theme.SMALL_PADDING
import com.example.greenhub.ui.theme.ShimmerDarkGray
import com.example.greenhub.ui.theme.ShimmerLightGray
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    plants: LazyPagingItems<Plant>? = null
) {
    var message by remember {
        mutableStateOf("Find your Favorite Plant!")
    }
    var icon by remember {
        mutableIntStateOf(R.drawable.search_document)
    }

    if (error != null) {
        message = parseErrorMessage(error = error)
        icon = R.drawable.network_error
    }

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 0.6f else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = "Alpha Animation"
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
        alphaAnim = alphaAnim,
        icon = icon,
        message = message,
        plants = plants,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyContent(
    alphaAnim: Float,
    icon: Int,
    message: String,
    plants: LazyPagingItems<Plant>? = null
) {
    val scope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }

    PullToRefreshBox(
        state = refreshState,
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            plants?.refresh()
            isRefreshing = false
            scope.launch {
                refreshState.animateToHidden()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier
                        .size(120.dp)
                        .alpha(alpha = alphaAnim),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = if (isSystemInDarkTheme()) ShimmerLightGray else ShimmerDarkGray
                )
                Text(
                    modifier = Modifier
                        .padding(top = SMALL_PADDING)
                        .alpha(alpha = alphaAnim),
                    text = message,
                    color = if (isSystemInDarkTheme()) ShimmerLightGray else ShimmerDarkGray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}

fun parseErrorMessage(error: LoadState.Error): String {

    Log.d("ErrorEmpty", error.error.toString())

    return when (error.error) {

        is SocketTimeoutException -> {
            "Server Unavailable."
        }

        is ConnectException -> {
            "Internet Unavailable."
        }

        else -> {
            "Unknown Error."
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    EmptyScreen(error = LoadState.Error(SocketTimeoutException()))
}