package com.sistechnology.aurorapos2.feature_payment.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType

@Composable
fun PaymentTypeBox(
    paymentType: PaymentType,
    onClick: (PaymentType) -> Unit
) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(id = R.color.logo_blue)
            )
            .clickable {onClick(paymentType)}){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = paymentType.type, modifier = Modifier.weight(3f), style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, color = Color.White))
                Image(painter = painterResource(id = R.drawable.cash_white), contentDescription = stringResource(
                    id = R.string.cash
                ), modifier = Modifier.weight(1f))
            }
        }
}