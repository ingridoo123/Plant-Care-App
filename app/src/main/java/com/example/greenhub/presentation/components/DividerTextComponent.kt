package com.example.greenhub.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DividerTextComponent() {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Divider(
            modifier = Modifier
                .weight(0.25f),
            thickness = 1.dp,
            color = Color.White
        )
        Text(text = "or", fontSize = 14.sp, color = Color.White, modifier = Modifier.padding(9.dp))

        Divider(
            modifier = Modifier
                .weight(0.25f),
            thickness = 1.dp,
            color = Color.White
        )
    }
}

@Composable
fun VerticalDividerTextComponent() {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Divider(
            modifier = Modifier
                .weight(0.25f)
                .width(1.dp), // Ustaw szerokość, aby Divider był pionowy
            thickness = 2.dp, // Kontroluje grubość w wymiarze poziomym
            color = Color.Black
        )
    }
}