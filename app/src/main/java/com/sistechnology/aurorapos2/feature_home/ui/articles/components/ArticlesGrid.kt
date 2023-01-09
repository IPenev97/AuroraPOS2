package com.sistechnology.aurorapos2.feature_home.ui.articles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article

@Composable
fun ArticlesGrid(
    articlesList: List<Article>,
    modifier: Modifier,
    onArticleClick: (article: Article) -> Unit,
    onArticleLongClick: (article: Article) -> Unit
) {

        LazyHorizontalGrid(
            modifier = modifier,
            rows = GridCells.Fixed(4),
            horizontalArrangement = Arrangement.Start,


            ) {
            items(articlesList, key = {article -> article.id}) { article ->
                ArticleBox(article = article, onArticleClick = {onArticleClick(it)}, onArticleLongClick = {onArticleLongClick(it)})
            }
        }
}