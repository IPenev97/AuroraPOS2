package com.sistechnology.aurorapos2.feature_home.ui.articles.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.R

@Composable
fun FavouriteArticlesButtons(
    onFavouriteClick: () -> Unit,
    onRecentlySoldClicked: () -> Unit,
    modifier: Modifier,
    selectedArticleGroupId: Int

) {
    var isFavouritesSelected by remember { mutableStateOf(false) }
    var isBestSellerSelected by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = selectedArticleGroupId){
        isFavouritesSelected = selectedArticleGroupId==0
        isBestSellerSelected = selectedArticleGroupId==-1
    }


        Column(modifier = Modifier) {
            IconButton(
                onClick = onFavouriteClick, modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f)
                    .weight(1f)
                    .background(if (isFavouritesSelected) colorResource(id = R.color.logo_blue) else Color.LightGray)

            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star Icon",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = onRecentlySoldClicked, modifier = Modifier
                    .padding(3.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f)
                    .weight(1f)
                    .background(if (isBestSellerSelected) colorResource(id = R.color.logo_blue) else Color.LightGray)

            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Best Seller Icon",
                    tint = Color.White
                )
            }
        }

}