package com.example.greenhub.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.greenhub.ui.theme.MEDIUM_PADDING
import com.example.greenhub.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.example.greenhub.ui.theme.SMALL_PADDING
import com.example.greenhub.ui.theme.ShimmerDarkGray
import com.example.greenhub.ui.theme.ShimmerMediumGray
import com.example.greenhub.ui.theme.descriptionColor

@Composable
fun ShimmerEffect() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
        horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ){
        items(count = 8){
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem() {
    val transition = rememberInfiniteTransition(label = "Infinite")
    val alphaAnim by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    ShimmerItem(alpha = alphaAnim)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp),
        color = if (isSystemInDarkTheme())
            Color.Black else Color.White,
        shape = RoundedCornerShape(size = 10.dp),
        border = BorderStroke(width = 1.dp, MaterialTheme.colors.descriptionColor.copy(0.1f))
    ) {
        Column(
            modifier = Modifier
                .padding(all = MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha = alpha)
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
                    .height(NAME_PLACEHOLDER_HEIGHT),
                color = if (isSystemInDarkTheme())
                    ShimmerDarkGray else ShimmerMediumGray,
                shape = RoundedCornerShape(size = SMALL_PADDING)
            ) {}
        }
    }
}

@Composable
@Preview
fun ShimmerItemPreview() {
    AnimatedShimmerItem()
}

//@Composable
//@Preview(uiMode = UI_MODE_NIGHT_YES)
//fun ShimmerItemDarkPreview() {
//    AnimatedShimmerItem()
//}