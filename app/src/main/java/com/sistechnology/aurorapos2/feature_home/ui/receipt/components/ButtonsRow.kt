package com.sistechnology.aurorapos2.feature_home.ui.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
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
fun ButtonsRow(
    modifier: Modifier,
    onPayClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    onDiscountClicked: () -> Unit
) {
    val blueColor = colorResource(id = R.color.logo_blue)
    val textStyle = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 25.sp)
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(width = 4.dp, shape = RoundedCornerShape(8.dp), color = blueColor)
                .background(blueColor)
                .clickable(onClick = onDiscountClicked),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "%", style = textStyle.copy(fontSize = 40.sp), )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(width = 4.dp, shape = RoundedCornerShape(8.dp), color = blueColor)
                .background(blueColor)
                .clickable(onClick = onCancelClicked),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.cancel), style = textStyle, )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(5.dp)
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(width = 4.dp, shape = RoundedCornerShape(8.dp), color = blueColor)
                .background(blueColor)
                .clickable (onClick = onPayClicked),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stringResource(id = R.string.pay_here), style = textStyle )
        }
    }
}