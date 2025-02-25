package com.example.greenhub.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.greenhub.ui.theme.SMALL_PADDING

@Composable
fun OrderedList(
    title: String,
    items: List<String>,
    textColor: Color
) {
    Column {
        Text(
            modifier = Modifier.padding(bottom = SMALL_PADDING),
            text = title,
            color = textColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        for (index in items.indices) {
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "${index + 1}. ${items[index]}",
                color = textColor,
                fontSize = MaterialTheme.typography.body1.fontSize
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderedListPreview() {
    OrderedList(title = "Marvel", items = mutableListOf("Iron Man", "Ant-man","Lizard", "Vision", "Doctor Strange"), textColor = Color.Black)
}