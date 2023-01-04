package com.sistechnology.aurorapos2.feature_home.ui.receipt.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.core.domain.values.receipt.CurrentReceiptList
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt

@Composable
fun BasketsRow(
    basketList: List<Receipt>,
    getTotal: (index: Int) -> Double,
    modifier: Modifier,
    onClick: (Int) -> Unit,
    selectedBasketIndex: Int
) {
    Surface(modifier = modifier.padding(10.dp)) {
        LazyRow(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(basketList) { index, receipt ->
                BasketBox(receipt = receipt, total = getTotal(index), index = index, onClick = {onClick(index)}, selectedBasketIndex = selectedBasketIndex)
            }
        }
    }
}