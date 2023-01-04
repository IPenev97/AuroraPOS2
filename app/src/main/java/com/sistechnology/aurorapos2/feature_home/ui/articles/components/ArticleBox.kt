package com.sistechnology.aurorapos2.feature_home.ui.articles

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArticleBox(
    article: Article,
    onArticleClick: (article: Article) -> Unit,
    onArticleLongClick: (article: Article) -> Unit
) {

    Surface(
        elevation = 6.dp, shape = RoundedCornerShape(8.dp), modifier = Modifier
            .padding(5.dp)
            .border(
                width = 4.dp, shape = RoundedCornerShape(8.dp), color = colorResource(
                    id = R.color.logo_blue
                )
            )
            .combinedClickable (onClick = {onArticleClick(article)}, onLongClick = {onArticleLongClick(article)})
    ) {
        Column(modifier = Modifier.fillMaxSize().aspectRatio(1f).padding(5.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp, minWidth = 60.dp),
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = stringResource(
                    id = R.string.articleImage
                )
            )
            Text(
                text = article.name,
                modifier = Modifier,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = article.price.toString() + "€",
                modifier = Modifier,
                fontWeight = FontWeight.Bold
            )
        }



    }

}