package com.sistechnology.aurorapos2.feature_authentication.ui.users.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_authentication.domain.models.User


@Composable
fun UserBox(
    user: User,
    onClick: () -> Unit
) {

    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val interactionSource = remember { MutableInteractionSource() }


    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp


    val constraints = ConstraintSet {
        val imageBox = createRefFor("imageBox")
        val nameBox = createRefFor("nameBox")

        constrain(nameBox) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(imageBox) {
            bottom.linkTo(nameBox.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

    }

    ConstraintLayout(
        constraints,
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null
            )
            { onClick() }
            .padding(20.dp)
    ) {

        Image(
            modifier = Modifier.layoutId("imageBox").width(screenWidth/10),
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Profile Image",
            alignment = Alignment.Center
        )

        TextField(
            value = user.username,
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account Icon",
                    tint = orangeColor
                )
            },
            textStyle = TextStyle(fontSize = 20.sp, color = orangeColor),
            onValueChange = {},
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
                .layoutId("nameBox")
                .offset(0.dp, 16.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = orangeColor,
                backgroundColor = orangeLightColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = CutCornerShape(12.dp)
        )
    }
}