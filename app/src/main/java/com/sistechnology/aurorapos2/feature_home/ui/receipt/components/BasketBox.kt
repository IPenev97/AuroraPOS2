package com.sistechnology.aurorapos2.feature_home.ui.receipt.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.domain.values.receipt.CurrentReceiptList
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt

@Composable
fun BasketBox(
    receipt: Receipt,
    index: Int,
    total: Double,
    onClick: () -> Unit,
    selectedBasketIndex: Int
) {

    var isSelected by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = selectedBasketIndex) {
        isSelected = index == selectedBasketIndex
    }



    Surface(
        modifier = Modifier
            .clickable(onClick = { onClick() })
            .fillMaxSize()
            .aspectRatio(12f / 8f)
            .padding(5.dp),
        shape = RoundedCornerShape(8.dp),
        color = if (isSelected) colorResource(id = R.color.logo_blue) else Color.LightGray
    ) {
        if (total != 0.0) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                Text(
                    text = "$total",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = " лв.",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center ) {
                Image(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 60.dp, minHeight = 60.dp)
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_shopping_cart_white),
                    contentDescription = "ShoppingCart"
                )
                Text(
                    text = receipt.basketNumber.toString(),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        color = Color.White
                    )
                )
            }
        }
    }


}