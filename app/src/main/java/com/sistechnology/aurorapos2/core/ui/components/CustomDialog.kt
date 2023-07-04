package com.sistechnology.aurorapos2.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.sistechnology.aurorapos2.R

@Composable
fun CustomDialog(
    cornerRadius: Dp = 12.dp,
    confirmButtonColor: Color = Color(0xFFFF0000),
    cancelButtonColor: Color = Color(0xFF35898F),
    imageVector: ImageVector? = null,
    painter: Painter = painterResource(id = R.drawable.logo),
    titleTextStyle: TextStyle = TextStyle(
        color = Color.Black.copy(alpha = 0.87f),
        fontFamily = FontFamily(Font(R.font.roboto_bold, FontWeight.Bold)),
        fontSize = 20.sp
    ),
    messageTextStyle: TextStyle = TextStyle(
        color = Color.Black.copy(alpha = 0.95f),
        fontFamily = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal)),
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    buttonTextStyle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_medium, FontWeight.Medium)),
        fontSize = 16.sp
    ),
    imageColor: Color = Color.Black,
    confirmButtonText: String,
    cancelButtonText: String? = null,
    messageText: String,
    titleText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {


    val interactionSource = remember {
        MutableInteractionSource()
    }

    val buttonCorner = 6.dp

    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = cornerRadius)
        ) {

            Column(modifier = Modifier.padding(all = 16.dp)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 6.dp,
                        alignment = Alignment.Start
                    )
                ) {

                   if(imageVector!=null) {
                       Icon(
                           modifier = Modifier.size(26.dp),
                           imageVector = imageVector,
                           contentDescription = "Icon",
                           tint = imageColor
                       )
                   } else {
                       Icon(
                           modifier = Modifier.size(26.dp),
                           painter = painter,
                           contentDescription = "Icon",
                       )
                   }

                    Text(
                        text = titleText,
                        style = titleTextStyle
                    )

                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 20.dp),
                    text = messageText,
                    style = messageTextStyle
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 10.dp,
                        alignment = Alignment.End
                    )
                ) {

                    if(cancelButtonText!=null) {
                        Box(
                            modifier = Modifier
                                .clickable(
                                ) {
                                    onDismiss()
                                }
                                .border(
                                    width = 1.dp,
                                    color = cancelButtonColor,
                                    shape = RoundedCornerShape(buttonCorner)
                                )
                                .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                        ) {
                            Text(
                                text = cancelButtonText,
                                style = buttonTextStyle,
                                color = cancelButtonColor
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clickable(
                            ) {
                                onConfirm()
                                onDismiss()
                            }
                            .background(
                                color = confirmButtonColor,
                                shape = RoundedCornerShape(buttonCorner)
                            )
                            .padding(top = 6.dp, bottom = 8.dp, start = 24.dp, end = 24.dp),
                    ) {
                        Text(
                            text = confirmButtonText,
                            style = buttonTextStyle,
                            color = Color.White
                        )
                    }

                }
            }

        }

    }
}