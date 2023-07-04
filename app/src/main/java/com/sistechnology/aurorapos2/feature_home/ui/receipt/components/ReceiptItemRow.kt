package com.sistechnology.aurorapos2.feature_home.ui.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem

@Composable
fun ReceiptItemRow(
    receiptItem: ReceiptItem,
    onItemClick: (ReceiptItem) -> Unit,




    ) {
    val textStyle = TextStyle(fontWeight = FontWeight.Medium, fontSize = 18.sp)
    val context = LocalContext.current
    val discountSign = if(receiptItem.discountType==DiscountType.Percent)"%" else context.getString(R.string.lv)

    Column(modifier = Modifier.clickable {onItemClick(receiptItem)}) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 50.dp, max = 70.dp), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = receiptItem.name,
                modifier = Modifier
                    .weight(3f)
                    .padding(10.dp),
                style = textStyle
            )
            Text(
                text = receiptItem.quantity.toString(),
                modifier = Modifier.weight(2f),
                style = textStyle
            )
            Text(
                text = "X",
                modifier = Modifier.weight(1f),
                style = textStyle
            )
            Text(
                text = String.format("%.2f", receiptItem.price),
                modifier = Modifier.weight(2f),
                style = textStyle
            )
            Text(
                text = if(receiptItem.discountValue!=0.0)String.format("-%.2f %s", receiptItem.discountValue, discountSign) else "",
                modifier = Modifier.weight(2f),
                style = textStyle,
                maxLines = 1,
                color = colorResource(id = R.color.delete_red)

            )

            Text(
                text = String.format("= %.2f", receiptItem.getTotal()),
                modifier = Modifier
                    .weight(2f)
                    .padding(10.dp),
                style = textStyle.copy(textAlign = TextAlign.End, color = if(receiptItem.discountValue!=0.0)colorResource(id = R.color.delete_red) else Color.Black)
            )
        }
        Divider(
            Modifier
                .background(Color.LightGray)
                .fillMaxWidth()
                .height(2.dp))



    }

}