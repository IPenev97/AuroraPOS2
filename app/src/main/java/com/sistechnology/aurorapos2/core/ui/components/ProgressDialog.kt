package com.sistechnology.aurorapos2.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.Center
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.sistechnology.aurorapos2.R


@Composable
fun ProgressDialog(
    title: String,
    onDismiss: () -> Unit,
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
) {

    androidx.compose.ui.window.Dialog(onDismissRequest = { onDismiss() },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment= Alignment.Center,
            modifier = Modifier
                .width(200.dp)
                .height(150.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(modifier = Modifier.padding(5.dp), text = title, style = titleTextStyle)
                CircularProgressIndicator()
            }

        }

    }
}