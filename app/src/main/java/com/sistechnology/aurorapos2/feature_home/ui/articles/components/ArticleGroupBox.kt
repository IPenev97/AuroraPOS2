package com.sistechnology.aurorapos2.feature_home.ui.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup

@Composable
fun ArticleGroupBox(
    articleGroup: ArticleGroup,
    onClick: (id: Int) -> Unit,
    selectedArticleGroupId: Int
) {

    var isSelected by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = selectedArticleGroupId){
        isSelected = selectedArticleGroupId==articleGroup.id
    }

    Surface(
        modifier = Modifier
            .clickable(
                onClick = {onClick(articleGroup.id)})
            .padding(3.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .aspectRatio(20f / 8f)
                .background(if (isSelected) colorResource(id = R.color.logo_blue) else Color.LightGray)
                .padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = articleGroup.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }

}