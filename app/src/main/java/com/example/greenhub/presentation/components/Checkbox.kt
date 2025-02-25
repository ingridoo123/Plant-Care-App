package com.example.greenhub.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckboxComponent(value: String, onCheckedChange: (Boolean) -> Unit ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val checkedState = remember {
            mutableStateOf(false)
        }
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
                onCheckedChange.invoke(it)
                              },
            colors = CheckboxDefaults.colors(

                checkedColor = Color.Transparent,
                checkmarkColor = Color.White,

            )
        )
        ClickableTextComponent(value = value)
    }
}
@Composable
fun LoginCheckboxComponent(value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Left
        ) {
            val checkedState = remember {
                mutableStateOf(false)
            }
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {checkedState.value = !checkedState.value},
                colors = CheckboxDefaults.colors(

                    checkedColor = Color.Transparent,
                    checkmarkColor = Color.White,

                    )
            )
            Text(text = "Pamiętaj mnie", fontSize = 13.sp, color = Color.White.copy(0.8f))
        }
        Text(text = "Zapomniałeś hasła?", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }

}

@Composable
fun ClickableTextComponent(value:String) {

    val initialText = "Zapoznałem się z "
    val privacyPolicyText = "Polityką Prywatności"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.White)) {
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also {
                Log.d("ClickableText", "{$it}")

            }

    },
        style = TextStyle(Color.White.copy(0.8f), fontSize = 13.sp)
    )
}
@Composable
fun ClickableLoginTextComponent(onTextSelected: (String) -> Unit) {

    val initialText = "Masz już konto? "
    val loginText = "Zaloguj się"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.White)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also {
                Log.d("ClickableText", "{$it}")
                if(it.item == loginText) {
                    onTextSelected(it.item)
                }

            }

    },
        style = TextStyle(Color.White.copy(0.8f), fontSize = 13.sp)
    )
}
@Composable
fun ClickableSignUpTextComponent(onTextSelected: (String) -> Unit) {

    val initialText = "Nie masz jeszcze konta? "
    val loginText = "Stwórz je"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold, color = Color.White)) {
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    ClickableText(text = annotatedString, onClick = { offset ->
        annotatedString.getStringAnnotations(offset,offset)
            .firstOrNull()?.also {
                Log.d("ClickableText", "{$it}")
                if(it.item == loginText) {
                    onTextSelected(it.item)
                }

            }

    },
        style = TextStyle(Color.White.copy(0.8f), fontSize = 13.sp)
    )
}