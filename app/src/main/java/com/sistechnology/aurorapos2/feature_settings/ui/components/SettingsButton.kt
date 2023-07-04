package com.sistechnology.aurorapos2.feature_settings.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R

@Composable
fun SettingsButton(
    drawableId: Int,
    backGroundColor: Color = colorResource(id = R.color.logo_blue),
    onClick: () -> Unit,
    horizontalBoxSize: Dp = 200.dp,
    horizontalImageSize: Dp = 120.dp,
    verticalImageSize: Dp = 60.dp,
    text: String
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(horizontalBoxSize)
            .clip(RoundedCornerShape(12.dp))
            .background(color = backGroundColor)
            .padding(5.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(horizontalImageSize),
            painter = painterResource(id = drawableId),
            contentDescription = "Genera icon",
            colorFilter = ColorFilter.tint(Color.White)
        )
        Text(
            text = text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.White
            )
        )
    }

}