import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.CustomSwitch
import com.sistechnology.aurorapos2.core.ui.components.IconTextField
import com.sistechnology.aurorapos2.core.ui.components.OkayCancelButtonsRow
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ReceiptValidation
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItemInfo

@Composable
fun EditReceiptItemBox(
    receiptItemInfo: ReceiptItemInfo,
    onDismiss: () -> Unit,
    onDeleteClicked: () -> Unit,
    onQuantityEntered: (String) -> Unit,
    onPlusPressed: () -> Unit,
    onMinusPressed: () -> Unit,
    onSaveClicked: () -> Unit,
    onDiscountEntered: (String) -> Unit,
    onDiscountTypeChanged: () -> Unit

) {


    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val blueColor = colorResource(id = R.color.logo_blue)

    val context = LocalContext.current


    var quantityError by remember { mutableStateOf("") }
    var discountError by remember { mutableStateOf("") }



    LaunchedEffect(key1 = receiptItemInfo) {
        quantityError = when (receiptItemInfo.quantityError) {
            ReceiptValidation.InvalidQuantityRange -> context.getString(R.string.edit_receipt_item_invalid_quantity_range)
            ReceiptValidation.InvalidQuantityFormat -> context.getString(R.string.edit_receipt_item_invalid_quantity_format)
            ReceiptValidation.MissingQuantity -> context.getString(R.string.edit_receipt_item_missing_quantity)
            else -> ""
        }
        discountError = when (receiptItemInfo.discountValueError) {
            ReceiptValidation.InvalidDiscountRange -> {
                context.getString(R.string.edit_receipt_item_invalid_discount_range_1) +
                        if (receiptItemInfo.discountType == DiscountType.Sum) receiptItemInfo.getTotalWithoutDiscount() else "100"
            }
            ReceiptValidation.InvalidDiscountFormat -> context.getString(R.string.edit_receipt_item_invalid_discount_format)
            else -> ""
        }
    }


    Surface(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .fillMaxHeight(0.6f),
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { onDeleteClicked() }) {

                Icon(
                    modifier = Modifier.size(40.dp).padding(5.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Minus Icon",
                    tint = colorResource(id = R.color.delete_red)

                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp)

            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = receiptItemInfo.name,
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = orangeColor
                        )
                    )
                    Text(
                        text = stringResource(R.string.total) + receiptItemInfo.getTotal(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = orangeColor
                        )
                    )
                }
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onMinusPressed() }) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = painterResource(id = R.drawable.ic_remove),
                            contentDescription = "Minus Icon",
                            tint = orangeColor
                        )
                    }
                    IconTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        value = receiptItemInfo.quantity,
                        inputType = KeyboardType.Number,
                        errorMessage = quantityError,

                        onValueChange = { onQuantityEntered(it) },
                        label = stringResource(id = R.string.quantity)
                    )
                    IconButton(onClick = { onPlusPressed() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            modifier = Modifier.size(40.dp),
                            contentDescription = "Plus Icon",
                            tint = orangeColor
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        modifier = Modifier
                            .weight(0.7f)
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomSwitch(
                            modifier = Modifier
                                .padding(3.dp)
                                .fillMaxSize()
                                .height(50.dp),
                            onClick = { onDiscountTypeChanged() },
                            color = orangeColor,
                            rightText = stringResource(id = R.string.sum),
                            leftText = stringResource(id = R.string.percent),
                            switchedOn = receiptItemInfo.discountType == DiscountType.Sum
                        )
                    }
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .padding(5.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconTextField(
                            modifier = Modifier,
                            value = receiptItemInfo.discountValue,
                            label = stringResource(id = R.string.discount_value),
                            labelSize = 12,
                            textSize = 20,
                            onValueChange = { onDiscountEntered(it) },
                            errorMessage = discountError,
                            inputType = KeyboardType.Number
                        )
                    }
                    OkayCancelButtonsRow(
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxSize(1f)
                            .padding(5.dp),
                        onCancel = onDismiss,
                        onOk = { onSaveClicked() },
                        okayBoxColor = orangeColor,
                        cancelBoxColor = orangeLightColor,
                        cancelTextColor = orangeColor,
                        dismissedSaveButton = receiptItemInfo.hasErrors()
                    )


                }

            }

        }


    }
}