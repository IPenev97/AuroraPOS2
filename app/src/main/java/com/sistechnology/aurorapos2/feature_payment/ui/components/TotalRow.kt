package com.sistechnology.aurorapos2.feature_payment.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.utils.CurrencyAmountInputVisualTransformation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TotalRow(
    total: Double,
    payment: String,
    onPaymentChange: (amount: String) -> Unit,
) {
    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by remember { mutableStateOf(TextFieldValue("")) }
    LaunchedEffect(key1 = payment){
        text = text.copy(text = payment)
    }


    Surface( shape = RoundedCornerShape(8.dp), border = BorderStroke(4.dp, orangeColor)){
        Row( modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextField(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                value = total.toString(),
                label = { Text(text = "Total", color = orangeColor, fontSize = 16.sp) },
                enabled = false,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Payment,
                        contentDescription = "Payment Icon",
                        tint = orangeColor
                    )
                },
                textStyle = TextStyle(fontSize = 25.sp, color = orangeColor),
                onValueChange = {},
                colors = TextFieldDefaults.textFieldColors(
                    textColor = orangeColor,
                    backgroundColor = orangeLightColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .offset(0.dp, -(3).dp),
                textStyle = TextStyle(fontSize = 25.sp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = orangeColor,
                    backgroundColor = orangeLightColor,
                    focusedBorderColor = orangeColor,
                    unfocusedBorderColor = orangeColor,
                    cursorColor = orangeColor
                ),
                value = text,
                onValueChange = {
                    text = it; onPaymentChange(it.text)
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}),
                label = {
                    Text(
                        "Payment",
                        color = colorResource(id = R.color.logo_orange),
                        fontSize = 20.sp
                    )
                }

            )

        }

    }
}