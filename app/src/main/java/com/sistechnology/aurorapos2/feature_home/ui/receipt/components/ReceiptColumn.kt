package com.sistechnology.aurorapos2.feature_home.ui.receipt

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem


@Composable
fun ReceiptColumn(
    receipt: Receipt,
    modifier: Modifier,
    onItemClick: (Int, ReceiptItem) -> Unit
) {
    val textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
    val discountSign = if(receipt.discountType==DiscountType.Percent) "%" else stringResource(id = R.string.lv)
    val discount = if(receipt.discountValue!=0.0) String.format("%s %.2f%s", stringResource(id = R.string.discount),receipt.discountValue, discountSign) else ""

    Box(
        modifier = modifier
            .fillMaxSize()
            .border(width = 3.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))

    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomEnd = 0.dp,
                            bottomStart = 0.dp
                        )
                    )
                    .background(Color.LightGray),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.article),
                    modifier = Modifier
                        .weight(3f)
                        .offset(10.dp),
                    style = textStyle
                )
                Text(
                    text = stringResource(id = R.string.quantity),
                    modifier = Modifier.weight(2f),
                    style = textStyle
                )
                Text(
                    text = "",
                    modifier = Modifier.weight(1f),
                    style = textStyle
                )
                Text(
                    text = stringResource(id = R.string.price),
                    modifier = Modifier.weight(2f),
                    style = textStyle
                )
                Text(
                    text = "",
                    modifier = Modifier.weight(2f),
                    style = textStyle
                )
                Text(
                    text = stringResource(id = R.string.sum),
                    modifier = Modifier
                        .weight(2f)
                        .offset(-(10).dp),
                    style = textStyle.copy(textAlign = TextAlign.End)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(8f)
            ) {
                LazyColumn() {
                    itemsIndexed(receipt.receiptItemList) { index, item ->
                        ReceiptItemRow(item, onItemClick = {onItemClick(index, item)} )
                    }
                }
            }
            Row( modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 8.dp
                    )
                )
                .background(Color.LightGray),
                verticalAlignment = Alignment.CenterVertically){
                Text(
                    text = stringResource(id = R.string.sum),
                    modifier = Modifier
                        .offset(10.dp)
                        .weight(1f),
                    style = textStyle
                )
                Text(text = discount,
                    modifier = Modifier.weight(1f),
                    style = textStyle.copy(color = colorResource(id = R.color.delete_red), textAlign = TextAlign.Start))
                Text(
                    text = String.format("%.2f", receipt.getTotal()),
                    modifier = Modifier
                        .offset(-(10).dp)
                        .weight(1f),
                    style = textStyle.copy(textAlign = TextAlign.End)
                )
            }


        }
    }
}