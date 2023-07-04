package com.sistechnology.aurorapos2.feature_home.ui.receipt.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.CustomSwitch
import com.sistechnology.aurorapos2.core.ui.components.IconTextField
import com.sistechnology.aurorapos2.core.ui.components.OkayCancelButtonsRow
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ReceiptValidation
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptInfo

@Composable
fun DiscountBox(
    receiptInfo: ReceiptInfo,
    onSave: () -> Unit,
    onDiscountEntered: (String) -> Unit,
    onDiscountTypeChanged: () -> Unit,
    onDismiss: () -> Unit
) {
    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val blueColor = colorResource(id = R.color.logo_blue)
    val redColor = colorResource(id = R.color.delete_red)

    val context = LocalContext.current
    var discountError by remember { mutableStateOf("") }






    LaunchedEffect(key1 = receiptInfo){
        discountError = when (receiptInfo.discountValueError) {
            ReceiptValidation.InvalidDiscountRange -> {
                context.getString(R.string.edit_receipt_item_invalid_discount_range_1) +
                        if (receiptInfo.discountType == DiscountType.Sum) receiptInfo.sumWithoutDiscount else "100"
            }
            ReceiptValidation.InvalidDiscountFormat -> context.getString(R.string.edit_receipt_item_invalid_discount_format)
            else -> ""
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight(0.43f)
            .padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .weight(1f)
                .padding(5.dp), verticalAlignment = Alignment.CenterVertically){

                    Text(
                        text = stringResource(R.string.total) + String.format("%.2f", receiptInfo.getTotal()),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = orangeColor
                        ))

            }
            Row(modifier = Modifier
                .weight(1f)
                .padding(5.dp), verticalAlignment = Alignment.CenterVertically) {
                CustomSwitch(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = { onDiscountTypeChanged() },
                    color = orangeColor,
                    rightText = stringResource(id = R.string.sum),
                    leftText = stringResource(id = R.string.percent),
                    switchedOn = receiptInfo.discountType == DiscountType.Sum
                )
            }
            Row(
                modifier = Modifier
                    .weight(1.4f)
                    .padding(5.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                IconTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = receiptInfo.discountValue,
                    label = stringResource(id = R.string.discount_value),
                    labelSize = 12,
                    textSize = 20,
                    onValueChange = { onDiscountEntered(it) },
                    errorMessage = discountError,
                )
            }
            OkayCancelButtonsRow(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(1f)
                    .padding(5.dp),
                onCancel = onDismiss,
                onOk = {onSave()},
                okayBoxColor = orangeColor,
                cancelBoxColor = orangeLightColor,
                cancelTextColor = orangeColor,
                dismissedSaveButton = receiptInfo.hasErrors()
            )


        }
    }


}