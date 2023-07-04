package com.sistechnology.aurorapos2.feature_home.ui.receipt.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt

@Composable
fun BasketsRow(
    basketList: List<Receipt>,
    getTotal: (index: Int) -> Double,
    modifier: Modifier,
    onClick: (Int) -> Unit,
    selectedBasketIndex: Int
) {
    Surface(modifier = modifier) {
        LazyRow(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(basketList) { index, receipt ->
                BasketBox(receipt = receipt, total = getTotal(index), index = index, onClick = {onClick(index)}, selectedBasketIndex = selectedBasketIndex)
            }
        }
    }
}