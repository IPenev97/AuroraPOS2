package com.sistechnology.aurorapos2.feature_home.ui.articles.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditArticleBox(
    article: Article?,
    onDismiss: () -> Unit
) {


    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val blueColor = colorResource(id = R.color.logo_blue)
    val redColor = colorResource(id = R.color.delete_red)

    val focusManager = LocalFocusManager.current


    var name by remember { mutableStateOf(article?.name) }
    var price by remember { mutableStateOf(article?.price.toString()) }


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
        Box(modifier = Modifier.fillMaxSize().padding(5.dp), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = redColor,
                    modifier = Modifier
                        .size(50.dp)
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

                    TextField(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        value = name ?: "",
                        label = {
                            Text(
                                text = stringResource(id = R.string.name),
                                color = orangeColor,
                                fontSize = 16.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Name Icon",
                                tint = orangeColor
                            )
                        },
                        textStyle = TextStyle(fontSize = 25.sp, color = orangeColor),
                        onValueChange = { name = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = orangeColor,
                            backgroundColor = orangeLightColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    TextField(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        value = price,
                        label = {
                            Text(
                                text = stringResource(id = R.string.name),
                                color = orangeColor,
                                fontSize = 16.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Money,
                                contentDescription = "Price Icon",
                                tint = orangeColor
                            )
                        },
                        textStyle = TextStyle(fontSize = 25.sp, color = orangeColor),
                        onValueChange = { price = it },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = orangeColor,
                            backgroundColor = orangeLightColor,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { focusManager.clearFocus() }),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done,
                            keyboardType = KeyboardType.Number
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }


        }


    }
}