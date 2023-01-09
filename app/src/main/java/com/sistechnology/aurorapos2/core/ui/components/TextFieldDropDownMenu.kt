package com.sistechnology.aurorapos2.core.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldDropDownMenu(
    modifier: Modifier,
    value: String,
    label: String,
    textColor: Color = colorResource(id = R.color.logo_orange),
    backGroundColor: Color = textColor.copy(TextFieldDefaults.BackgroundOpacity),
    dropDownValues: List<String>,
    onItemClick: (String) -> Unit,
    labelTextSize: Int = 16,
    textSize: Int = 25

    ) {
    var expanded by remember {mutableStateOf(false)}
    ExposedDropdownMenuBox(
        modifier = modifier.clip(RoundedCornerShape(12.dp)),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        TextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = label, color = textColor, fontSize = labelTextSize.sp) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                backgroundColor = backGroundColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = textSize.sp, color = textColor)
        )

        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dropDownValues.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {onItemClick(selectedOption); expanded=false}) {
                    Text(text = selectedOption, color = textColor, fontSize = textSize.sp)
                }
            }
        }
    }

}