package com.sistechnology.aurorapos2.feature_payment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_payment.domain.models.Payment

@Composable
fun PaymentRow(
    payment: Payment,
    onDeleteClick: (Payment) -> Unit
) {
    val textStyle = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
    Column() {
        Row (verticalAlignment = Alignment.CenterVertically){
            Text(modifier = Modifier
                .weight(1f)
                .padding(10.dp), text = payment.type.type, style = textStyle.copy(textAlign = TextAlign.Start))
            Text(modifier = Modifier
                .weight(1f), text = payment.amount.toString(), style = textStyle.copy(textAlign = TextAlign.Justify))
            IconButton(modifier = Modifier, onClick = {onDeleteClick(payment)}) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(id = R.string.deleteIcon), tint = colorResource(
                    id = R.color.delete_red
                ))
            }
        }
        Divider(
            Modifier
                .background(colorResource(id = R.color.logo_blue))
                .fillMaxWidth()
                .height(2.dp))

    }

}