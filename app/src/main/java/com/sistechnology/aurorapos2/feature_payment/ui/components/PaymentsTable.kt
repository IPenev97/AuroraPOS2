package com.sistechnology.aurorapos2.feature_payment.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun PaymentsTable(
    paymentsList: List<Payment>,
    onDeletePayment: (Payment) -> Unit,
    total: Double,
    totalPayed: Double
) {



    var isChange by remember{mutableStateOf(false)}

    val textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.White)
    val totalTextStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 25.sp, color = Color.White)

    LaunchedEffect(key1 = totalPayed){
        isChange = total<totalPayed
    }

    Surface(
        modifier = Modifier.padding(5.dp),
        elevation = 10.dp, shape = RoundedCornerShape(8.dp), border = BorderStroke(
            4.dp, colorResource(
                id = R.color.logo_blue
            )
        )
    ) {
        Column(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.5f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .background(colorResource(id = R.color.logo_blue)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                        .offset(10.dp),
                    text = stringResource(id = R.string.payment_type),
                    style = textStyle.copy(textAlign = TextAlign.Start)
                )
                Text(
                    modifier = Modifier
                        .padding(5.dp)
                        .offset(-(33).dp, 0.dp)
                        .weight(1f), text = stringResource(id = R.string.amount), style = textStyle
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                LazyColumn() {
                    items(paymentsList) { payment ->
                        PaymentRow(payment, {onDeletePayment(payment)})
                    }
                }
            }
            Row(modifier = Modifier
                .weight(1.5f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 8.dp
                    )
                )
                .background(colorResource(id = R.color.logo_blue)),
            verticalAlignment = Alignment.CenterVertically){
                Text(modifier = Modifier
                    .padding(3.dp)
                    .offset(10.dp)
                    .weight(1f), text =
                if(isChange){stringResource(id = R.string.change)}else{
                    stringResource(id = R.string.amount_to_pay)} ,
                    style = totalTextStyle.copy(textAlign = TextAlign.Start))
                Text(modifier = Modifier
                    .padding(3.dp)
                    .offset(-(10).dp)
                    .weight(1f), text = if(isChange){(totalPayed-total).toString()} else {(total-totalPayed).toString()}, style = totalTextStyle.copy(textAlign = TextAlign.End))
            }



        }
    }

}