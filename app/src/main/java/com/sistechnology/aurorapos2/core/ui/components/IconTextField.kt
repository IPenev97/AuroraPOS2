package com.sistechnology.aurorapos2.core.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R

@Composable
fun IconTextField(
    modifier: Modifier,
    textIconColor: Color = colorResource(id = R.color.logo_orange),
    backGroundColor: Color = textIconColor.copy(TextFieldDefaults.BackgroundOpacity),
    value: String,
    label: String,
    icon: ImageVector = Icons.Default.Edit,
    onValueChange: (String) -> Unit,
    textSize: Int = 25,
    labelSize: Int = 16,
    inputType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true
    ) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        label = {
            Text(
                text = label,
                color = textIconColor,
                fontSize = labelSize.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Deafult Icon",
                tint = textIconColor
            )
        },
        textStyle = TextStyle(fontSize = textSize.sp, color = textIconColor),
        onValueChange = { onValueChange(it) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = textIconColor,
            backgroundColor = backGroundColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = inputType
        ),
        shape = RoundedCornerShape(12.dp)
    )
}