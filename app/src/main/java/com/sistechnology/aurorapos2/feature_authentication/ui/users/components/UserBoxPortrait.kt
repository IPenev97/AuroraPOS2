package com.sistechnology.aurorapos2.feature_authentication.ui.users.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_authentication.domain.models.User

@Composable
fun UserBoxPortrait(
    user: User,
    onClick: () -> Unit
) {

    val orangeColor = colorResource(id = R.color.logo_orange)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)
    val interactionSource = remember { MutableInteractionSource() }


    val constraints = ConstraintSet {
        val imageBox = createRefFor("imageBox")
        val nameBox = createRefFor("nameBox")

        constrain(nameBox) {
            start.linkTo(imageBox.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }
        constrain(imageBox) {


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
            .padding(10.dp)
    ) {
        Image(
            modifier = Modifier.layoutId("imageBox").width(120.dp),
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Profile Image",
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
                .fillMaxWidth(0.6f)
                .height(60.dp)
                .layoutId("nameBox")
                .offset(16.dp, 0.dp),
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