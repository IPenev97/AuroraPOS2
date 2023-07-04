package com.sistechnology.aurorapos2.feature_home.ui.articles.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.IconTextField
import com.sistechnology.aurorapos2.core.ui.components.OkayCancelButtonsRow
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ArticleValidation

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditArticleBox(
    articleInfo: ArticleInfo,
    onDismiss: () -> Unit,
    onSave: (articleInfo: ArticleInfo) -> Unit,
    onNameEntered: (String) -> Unit,
    onPriceEntered: (String) -> Unit,
    articleGroupList: List<ArticleGroup>,
    vatGroupList: List<VatGroup>
) {


    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)

    val context = LocalContext.current


    var favourite by remember { mutableStateOf(articleInfo.favourite) }


    var articleGroupExpanded by remember { mutableStateOf(false) }
    var vatGroupExpanded by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf("") }
    var priceError by remember { mutableStateOf("") }


    LaunchedEffect(key1 = articleInfo) {
        priceError = when(articleInfo.priceError){
            ArticleValidation.MissingPrice -> context.getString(R.string.edit_article_missing_price)
            ArticleValidation.InvalidPriceFormat -> context.getString(R.string.edit_article_invalid_price_format)
            ArticleValidation.InvalidPriceRange -> context.getString(R.string.edit_article_invalid_price_range)
            else -> ""
        }
        nameError = when(articleInfo.nameError){
            ArticleValidation.MissingName -> context.getString(R.string.edit_article_missing_name)
            ArticleValidation.InvalidName -> context.getString(R.string.edit_article_invalid_name)
            else -> ""
        }
    }



    Surface(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp
    )
    {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { favourite.let { favourite = !it } }) {
                if (favourite)
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.favourite_star_filled),
                        contentDescription = "Star Icon"
                    )
                else
                    Image(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(5.dp),
                        painter = painterResource(id = R.drawable.favourite_star_outlined),
                        contentDescription = "Star Icon"
                    )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Row(modifier = Modifier.weight(2f)) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.shopping_cart),
                    contentDescription = stringResource(
                        id = R.string.article
                    )
                )
            }
            Row(modifier = Modifier.weight(8f)) {
                Column() {
                    IconTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        value = articleInfo.name ?: "",
                        label = stringResource(id = R.string.name),
                        onValueChange = { onNameEntered(it) },
                        errorMessage = nameError
                    )
                    IconTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        value = articleInfo.price ?: "0.00",
                        label = stringResource(id = R.string.price),
                        errorMessage = priceError,
                        onValueChange = { onPriceEntered(it) },
                        icon = Icons.Default.Money,
                        inputType = KeyboardType.Number
                    )

                    Row() {
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            expanded = articleGroupExpanded,
                            onExpandedChange = {
                                articleGroupExpanded = !articleGroupExpanded
                            }
                        ) {
                            TextField(
                                value = articleInfo.groupName?:"",
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.group),
                                        color = orangeColor,
                                        fontSize = 16.sp
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = articleGroupExpanded,
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = orangeColor,
                                    backgroundColor = orangeLightColor,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(fontSize = 25.sp, color = orangeColor)
                            )
                            ExposedDropdownMenu(
                                expanded = articleGroupExpanded,
                                onDismissRequest = { articleGroupExpanded = false }
                            ) {
                                articleGroupList.forEach { selectedOption ->
                                    DropdownMenuItem(onClick = {
                                        articleInfo.groupName = selectedOption.name; articleInfo.groupId =
                                        selectedOption.id; articleGroupExpanded = false
                                    }) {
                                        Text(
                                            text = selectedOption.name,
                                            color = orangeColor,
                                            fontSize = 25.sp
                                        )
                                    }
                                }
                            }
                        }
                        ExposedDropdownMenuBox(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(5.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            expanded = vatGroupExpanded,
                            onExpandedChange = {
                                vatGroupExpanded = !vatGroupExpanded
                            }
                        ) {
                            TextField(
                                value = articleInfo.vatGroupName ?: "",
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        text = stringResource(id = R.string.vat_group),
                                        color = orangeColor,
                                        fontSize = 16.sp
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = vatGroupExpanded,
                                    )
                                },
                                colors = TextFieldDefaults.textFieldColors(
                                    textColor = orangeColor,
                                    backgroundColor = orangeLightColor,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(fontSize = 25.sp, color = orangeColor)
                            )
                            ExposedDropdownMenu(
                                expanded = vatGroupExpanded,
                                onDismissRequest = { vatGroupExpanded = false }
                            ) {
                                vatGroupList.forEach { selectedOption ->
                                    DropdownMenuItem(onClick = {
                                        articleInfo.vatGroupName = selectedOption.group; articleInfo.vatGroupId =
                                        selectedOption.id; vatGroupExpanded = false
                                    }) {
                                        Text(
                                            text = selectedOption.group,
                                            color = orangeColor,
                                            fontSize = 25.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            OkayCancelButtonsRow(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                onCancel = onDismiss,
                onOk = {onSave(articleInfo)},
                dismissedSaveButton = articleInfo.hasErrors()
            )


        }


    }
}