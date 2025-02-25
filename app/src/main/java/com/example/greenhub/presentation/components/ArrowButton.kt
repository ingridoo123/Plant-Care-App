package com.example.greenhub.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.greenhub.ui.theme.descriptionColor

@Composable
fun BackArrowButton(onClick: () -> Unit,modifier: Modifier) {
    OutlinedButton(
        onClick = onClick,
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.White),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            Modifier.size(19.dp)
        )
    }
}

@Composable
fun BackArrowButton2(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier
            .size(35.dp)
            .shadow(shape = RoundedCornerShape(20.dp), elevation = 15.dp, spotColor = Color.Black, ambientColor = Color.Black, clip = true)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            Modifier.size(21.dp),
            tint = MaterialTheme.colors.descriptionColor
        )
    }
}

@Composable
fun HeartIcon(onClick: () -> Unit, favPlant: Boolean) {

    val iconImage = if (favPlant) HeartFilled else Heart

    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        contentPadding = PaddingValues(4.dp),
        modifier = Modifier
            .size(35.dp)
            .shadow(shape = RoundedCornerShape(20.dp), elevation = 15.dp, spotColor = Color.Black, ambientColor = Color.Black, clip = true)
    ) {
        Icon(
            imageVector = iconImage,
            contentDescription = null,
            Modifier.size(21.dp),
            tint = Color(0xFF13693D)
        )
    }
}

@Composable
fun MyTextFieldUsername(onTextSelected:(String) -> Unit, error: Boolean = false, errorMessage: String? = null) {

    var textValue by remember {
        mutableStateOf("")
    }
    val errorStatus = ! error && textValue.isNotEmpty()

    TextField(
        placeholder = { Text(text = "Podaj imię", fontSize = 13.sp)},
        value = textValue,
        onValueChange = {
            textValue = it
            onTextSelected(it)
                        },
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                if (errorStatus) Color.Red else Color.White.copy(0.8f),
                shape = RoundedCornerShape(10.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.White.copy(0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(0.5f)

        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            if(errorStatus) {
                errorMessage?.let {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = it, color = Color.Red, fontSize = 11.sp)
                    }
                }
            }
        },
        isError = errorStatus
    )

}

@Composable
fun MyTextFieldEmail(onTextSelected: (String) -> Unit, error: Boolean = false, errorMessage: String? = null) {

    var textValue by remember {
        mutableStateOf("")
    }
    val errorStatus = ! error && textValue.isNotEmpty()

    TextField(
        placeholder = { Text(text = "Wprowadź e-mail", fontSize = 13.sp) },
        value = textValue,
        onValueChange = {
            textValue = it
            onTextSelected(it)
                        },
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                if (errorStatus) Color.Red else Color.White.copy(0.8f),
                shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.White.copy(0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(0.5f)

        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            if(errorStatus) {
                errorMessage?.let {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = it, color = Color.Red, fontSize = 11.sp)
                    }
                }
            }
        },
        isError = errorStatus
    )

}

@Composable
fun MyTextFieldEmail2(onTextSelected: (String) -> Unit, error: Boolean = false, errorMessage: String? = null) {

    var textValue by remember {
        mutableStateOf("")
    }
    val errorStatus = ! error && textValue.isNotEmpty()

    TextField(
        placeholder = { Text(text = "Wprowadź e-mail", fontSize = 13.sp) },
        value = textValue,
        onValueChange = {
            textValue = it
            onTextSelected(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(53.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                Color.White.copy(0.8f),
                shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.White.copy(0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(0.5f)

        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        shape = RoundedCornerShape(10.dp),
        trailingIcon = {
            if(errorStatus) {
                errorMessage?.let {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = it, color = Color.Red, fontSize = 11.sp)
                    }
                }
            }
        },
        //isError = errorStatus
    )

}

@Composable
fun MyTextFieldPassword(labelText:String, onTextSelected: (String) -> Unit, error: Boolean = false, errorMessage: String? = null) {
    var textValue by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(true)
    }

    val errorStatus = ! error && textValue.isNotEmpty()

    TextField(
        placeholder = { Text(text = labelText, fontSize = 13.sp)},
        value = textValue,
        onValueChange = {
            textValue = it
            onTextSelected(it)
                        },
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                if (errorStatus) Color.Red else Color.White.copy(0.8f),
                shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.White.copy(0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(0.5f),
            trailingIconColor = Color.White.copy(0.5f)

        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            if (errorStatus) {
                errorMessage?.let {
                    Row(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = it, color = Color.Red, fontSize = 11.sp)
                    }
                    }
                }
        },
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if(passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        isError = errorStatus
    )

}

@Composable
fun MyTextFieldPasswordLogin(labelText:String, onTextSelected: (String) -> Unit, error: Boolean = false, errorMessage: String? = null) {
    var textValue by remember {
        mutableStateOf("")
    }
    var passwordVisible by remember {
        mutableStateOf(true)
    }

    val errorStatus = ! error && textValue.isNotEmpty()

    TextField(
        placeholder = { Text(text = labelText, fontSize = 13.sp)},
        value = textValue,
        onValueChange = {
            textValue = it
            onTextSelected(it)
        },
        modifier = Modifier
            .height(53.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                1.dp,
                Color.White.copy(0.8f),
                shape = RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            textColor = Color.White,
            cursorColor = Color.White.copy(0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            placeholderColor = Color.White.copy(0.5f),
            trailingIconColor = Color.White.copy(0.5f)

        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        trailingIcon = {
            val iconImage = if(passwordVisible) Eye else EyeOff
            IconButton(onClick = { passwordVisible = !passwordVisible}) {
                Icon(imageVector = iconImage, contentDescription = null, modifier = Modifier.size(24.dp))
            }
        },
        shape = RoundedCornerShape(10.dp),
        visualTransformation = if(passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        //isError = errorStatus
    )

}




public val Eye: ImageVector
    get() {
        if (_Eye != null) {
            return _Eye!!
        }
        _Eye = ImageVector.Builder(
            name = "Eye",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(2.062f, 12.348f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -0.696f)
                arcToRelative(10.75f, 10.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.876f, 0f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0.696f)
                arcToRelative(10.75f, 10.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -19.876f, 0f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(15f, 12f)
                arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 15f)
                arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9f, 12f)
                arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15f, 12f)
                close()
            }
        }.build()
        return _Eye!!
    }

private var _Eye: ImageVector? = null


public val EyeOff: ImageVector
    get() {
        if (_EyeOff != null) {
            return _EyeOff!!
        }
        _EyeOff = ImageVector.Builder(
            name = "EyeOff",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(10.733f, 5.076f)
                arcToRelative(10.744f, 10.744f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11.205f, 6.575f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 0.696f)
                arcToRelative(10.747f, 10.747f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.444f, 2.49f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.084f, 14.158f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -4.242f, -4.242f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(17.479f, 17.499f)
                arcToRelative(10.75f, 10.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, -15.417f, -5.151f)
                arcToRelative(1f, 1f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -0.696f)
                arcToRelative(10.75f, 10.75f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.446f, -5.143f)
            }
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF000000)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(2f, 2f)
                lineToRelative(20f, 20f)
            }
        }.build()
        return _EyeOff!!
    }

private var _EyeOff: ImageVector? = null


public val HeartFilled: ImageVector
    get() {
        if (_HeartFilled != null) {
            return _HeartFilled!!
        }
        _HeartFilled = ImageVector.Builder(
            name = "HeartFilled",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.Green),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.88f, 4.78079f)
                curveTo(14.7993f, 4.465f, 14.6748f, 4.162f, 14.51f, 3.8808f)
                curveTo(14.3518f, 3.5882f, 14.1493f, 3.3217f, 13.91f, 3.0907f)
                curveTo(13.563f, 2.7449f, 13.152f, 2.4698f, 12.7f, 2.2808f)
                curveTo(11.7902f, 1.9074f, 10.7698f, 1.9074f, 9.86f, 2.2808f)
                curveTo(9.4328f, 2.4616f, 9.0403f, 2.7154f, 8.7f, 3.0308f)
                lineTo(8.65003f, 3.09073f)
                lineTo(8.00001f, 3.74075f)
                lineTo(7.34999f, 3.09073f)
                lineTo(7.3f, 3.03079f)
                curveTo(6.9597f, 2.7154f, 6.5673f, 2.4616f, 6.14f, 2.2808f)
                curveTo(5.2302f, 1.9074f, 4.2098f, 1.9074f, 3.3f, 2.2808f)
                curveTo(2.848f, 2.4698f, 2.4371f, 2.7449f, 2.09f, 3.0907f)
                curveTo(1.8505f, 3.324f, 1.6451f, 3.59f, 1.48f, 3.8808f)
                curveTo(1.3226f, 4.1644f, 1.2016f, 4.4668f, 1.12f, 4.7808f)
                curveTo(1.0352f, 5.1072f, 0.9949f, 5.4436f, 1f, 5.7808f)
                curveTo(1.0005f, 6.0979f, 1.0408f, 6.4136f, 1.12f, 6.7207f)
                curveTo(1.2038f, 7.0308f, 1.3247f, 7.3296f, 1.48f, 7.6108f)
                curveTo(1.6477f, 7.8998f, 1.8529f, 8.1654f, 2.09f, 8.4008f)
                lineTo(8.00001f, 14.3108f)
                lineTo(13.91f, 8.40079f)
                curveTo(14.1471f, 8.1678f, 14.3492f, 7.9017f, 14.51f, 7.6108f)
                curveTo(14.6729f, 7.3321f, 14.7974f, 7.0327f, 14.88f, 6.7207f)
                curveTo(14.9592f, 6.4136f, 14.9995f, 6.0979f, 15f, 5.7808f)
                curveTo(15.0052f, 5.4436f, 14.9648f, 5.1072f, 14.88f, 4.7808f)
                close()
            }
        }.build()
        return _HeartFilled!!
    }

private var _HeartFilled: ImageVector? = null


public val Heart: ImageVector
    get() {
        if (_Heart != null) {
            return _Heart!!
        }
        _Heart = ImageVector.Builder(
            name = "Heart",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF13693D)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14.88f, 4.78f)
                arcToRelative(3.489f, 3.489f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.37f, -0.9f)
                arcToRelative(3.24f, 3.24f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.6f, -0.79f)
                arcToRelative(3.78f, 3.78f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.21f, -0.81f)
                arcToRelative(3.74f, 3.74f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.84f, 0f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.16f, 0.75f)
                lineToRelative(-0.05f, 0.06f)
                lineToRelative(-0.65f, 0.65f)
                lineToRelative(-0.65f, -0.65f)
                lineToRelative(-0.05f, -0.06f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.16f, -0.75f)
                arcToRelative(3.74f, 3.74f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.84f, 0f)
                arcToRelative(3.78f, 3.78f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.21f, 0.81f)
                arcToRelative(3.55f, 3.55f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.97f, 1.69f)
                arcToRelative(3.75f, 3.75f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.12f, 1f)
                curveToRelative(0f, 0.317f, 0.04f, 0.633f, 0.12f, 0.94f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.36f, 0.89f)
                arcToRelative(3.8f, 3.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.61f, 0.79f)
                lineTo(8f, 14.31f)
                lineToRelative(5.91f, -5.91f)
                curveToRelative(0.237f, -0.233f, 0.44f, -0.5f, 0.6f, -0.79f)
                arcTo(3.578f, 3.578f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15f, 5.78f)
                arcToRelative(3.747f, 3.747f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.12f, -1f)
                close()
                moveToRelative(-1f, 1.63f)
                arcToRelative(2.69f, 2.69f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.69f, 1.21f)
                lineToRelative(-5.21f, 5.2f)
                lineToRelative(-5.21f, -5.2f)
                arcToRelative(2.9f, 2.9f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.44f, -0.57f)
                arcToRelative(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.27f, -0.65f)
                arcToRelative(3.25f, 3.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.08f, -0.69f)
                arcTo(3.36f, 3.36f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.06f, 5f)
                arcToRelative(2.8f, 2.8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.27f, -0.65f)
                curveToRelative(0.12f, -0.21f, 0.268f, -0.4f, 0.44f, -0.57f)
                arcToRelative(2.91f, 2.91f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.89f, -0.6f)
                arcToRelative(2.8f, 2.8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.08f, 0f)
                curveToRelative(0.33f, 0.137f, 0.628f, 0.338f, 0.88f, 0.59f)
                lineToRelative(1.36f, 1.37f)
                lineToRelative(1.36f, -1.37f)
                arcToRelative(2.72f, 2.72f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.88f, -0.59f)
                arcToRelative(2.8f, 2.8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.08f, 0f)
                curveToRelative(0.331f, 0.143f, 0.633f, 0.347f, 0.89f, 0.6f)
                curveToRelative(0.174f, 0.165f, 0.32f, 0.357f, 0.43f, 0.57f)
                arcToRelative(2.69f, 2.69f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.35f, 1.34f)
                arcToRelative(2.6f, 2.6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.06f, 0.72f)
                horizontalLineToRelative(-0.03f)
                close()
            }
        }.build()
        return _Heart!!
    }

private var _Heart: ImageVector? = null


