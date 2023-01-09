package com.sistechnology.aurorapos2.feature_home.ui.articles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup

@Composable
fun ArticleGroupGrid(
    modifier: Modifier,
    articleGroupList: List<ArticleGroup>,
    selectedArticleGroupId: Int,
    onClick: (id: Int) -> Unit
) {

    LazyHorizontalGrid(
        modifier = modifier,
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.Start,


        ) {

        items(articleGroupList, key = { articleGroup -> articleGroup.id }) { articleGroup ->
            ArticleGroupBox(articleGroup = articleGroup, onClick = {onClick(it)}, selectedArticleGroupId)
        }
    }
}