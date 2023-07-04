package com.sistechnology.aurorapos2.feature_payment.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.IconTextField
import com.sistechnology.aurorapos2.feature_payment.domain.models.enums.PaymentValidation

@Composable
fun TotalRow(
    total: Double,
    payment: String,
    paymentValidation: PaymentValidation?,
    onPaymentEntered: (amount: String) -> Unit,
) {
    val orangeColor = colorResource(id = R.color.logo_orange)
    var paymentErrorMessage by remember{ mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(key1 = paymentValidation){
        paymentErrorMessage = when(paymentValidation){
            PaymentValidation.InvalidFormat -> context.getString(R.string.payment_inccorect_format)
            PaymentValidation.MissingPayment -> context.getString(R.string.payment_missing_amount)
            else -> ""
        }
    }





    Surface(modifier = Modifier.padding(5.dp).fillMaxSize(), shape = RoundedCornerShape(8.dp), border = BorderStroke(4.dp, orangeColor)){
        Row( modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
            IconTextField(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                value = String.format("%.2f", total),
                label = stringResource(R.string.total),
                onValueChange = {},
                icon = Icons.Default.Payment,
                enabled = false
            )
            IconTextField(
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f),
                value = payment,
                onValueChange = {onPaymentEntered(it)},
                inputType = KeyboardType.Number,
                label = stringResource(id = R.string.payment),
                errorMessage = paymentErrorMessage,


            )

        }

    }
}