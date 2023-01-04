package com.sistechnology.aurorapos2.feature_payment.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType

@Composable
fun PaymentTypesGrid(
     paymentTypes: List<PaymentType>,
     onClick: (PaymentType) -> Unit
) {
    Surface(elevation = 10.dp, shape = RoundedCornerShape(8.dp) ) {
        LazyColumn(modifier = Modifier.fillMaxHeight().fillMaxWidth(), horizontalAlignment = Alignment.Start){
            items(paymentTypes) { item ->
                PaymentTypeBox(paymentType = item, onClick = {onClick(it)})
            }
        }
    }
}