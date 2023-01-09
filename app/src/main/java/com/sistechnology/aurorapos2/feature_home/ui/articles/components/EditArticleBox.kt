package com.sistechnology.aurorapos2.feature_home.ui.articles.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.IconTextField
import com.sistechnology.aurorapos2.core.ui.components.TextFieldDropDownMenu
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun EditArticleBox(
    articleInfo: ArticleInfo?,
    onDismiss: () -> Unit,
    onSave: (articleInfo: ArticleInfo) -> Unit,
    articleGroupList: List<ArticleGroup>
) {


    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val blueColor = colorResource(id = R.color.logo_blue)
    val redColor = colorResource(id = R.color.delete_red)


    var name by remember { mutableStateOf(articleInfo?.name) }
    var price by remember { mutableStateOf(articleInfo?.price.toString()) }
    var articleGroupName by remember { mutableStateOf(articleInfo?.groupName) }
    var articleGroupId by remember {mutableStateOf(articleInfo?.groupId)}
    var articleGroupExpanded by remember {mutableStateOf(false)}


    Surface(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.8f),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(
            width = 4.dp, color = blueColor
        )
    )
    {
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
                        value = name ?: "",
                        label = stringResource(id = R.string.name),
                        onValueChange = { name = it })
                    IconTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        value = price,
                        label = stringResource(id = R.string.price),
                        onValueChange = { price = it},
                        icon = Icons.Default.Money,
                        inputType = KeyboardType.Number
                    )
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .width(400.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        expanded = articleGroupExpanded,
                        onExpandedChange = {
                            articleGroupExpanded = !articleGroupExpanded
                        }
                    ) {
                        TextField(
                            value = articleGroupName?:"",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text(text = stringResource(id = R.string.group), color = orangeColor, fontSize = 16.sp) },
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
                                DropdownMenuItem(onClick = {articleGroupName = selectedOption.name; articleGroupId = selectedOption.id; articleGroupExpanded=false}) {
                                    Text(text = selectedOption.name, color = orangeColor, fontSize = 25.sp)
                                }
                            }
                        }
                    }
                }
            }
            Row(modifier = Modifier.weight(1f)) {
                IconButton(
                    onClick = onDismiss, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .background(redColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
                IconButton(
                    onClick = {onSave(ArticleInfo(id = articleInfo?.id, name = name, price = price, groupId = articleGroupId))}, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(5.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        )
                        .background(Color.Green)
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "Close Icon",
                        tint = colorResource(R.color.okay_button_green),
                        modifier = Modifier
                            .size(50.dp)
                    )
                }
            }


        }


    }
}