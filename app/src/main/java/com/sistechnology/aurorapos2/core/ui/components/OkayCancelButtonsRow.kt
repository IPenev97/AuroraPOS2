package com.sistechnology.aurorapos2.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R

@Composable
fun OkayCancelButtonsRow(
    modifier: Modifier,
    textSize: Int = 25,
    okayBoxColor: Color = colorResource(id = R.color.logo_orange),
    cancelBoxColor: Color = okayBoxColor.copy(TextFieldDefaults.BackgroundOpacity),
    okayTextColor: Color = Color.White,
    cancelTextColor: Color = okayBoxColor,
    okayText: String = stringResource(id = R.string.save),
    cancelText: String = stringResource(id = R.string.cancel),
    onOk: () -> Unit,
    onCancel: () -> Unit,
    dismissedSaveButton: Boolean = false
) {
    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(3.dp)
                .clip(RoundedCornerShape(5.dp))
                .clickable(onClick = onCancel)
                .background(cancelBoxColor), contentAlignment = Alignment.Center
        ) {
            Text(
                text = cancelText,
                style = TextStyle(
                    color = cancelTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize.sp
                )
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(3.dp)
                .clickable(onClick = {if(!dismissedSaveButton) onOk() else {} })
                .clip(RoundedCornerShape(5.dp))
                .background(if (!dismissedSaveButton) okayBoxColor else Color.LightGray), contentAlignment = Alignment.Center
        ) {
            Text(
                text = okayText,
                style = TextStyle(
                    color = okayTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize.sp
                )

            )
        }

    }
}