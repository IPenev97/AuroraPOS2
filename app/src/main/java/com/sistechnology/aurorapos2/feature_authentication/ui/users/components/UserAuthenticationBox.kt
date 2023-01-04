package com.sistechnology.aurorapos2.feature_authentication.ui.users.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Login
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.feature_authentication.domain.models.User

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserAuthenticationBox(
    user: User?,
    onBackClicked: () -> Unit,
    onLoginClicked: (String) -> Unit
) {

    val configuration = LocalConfiguration.current

    val screenHeight = configuration.screenHeightDp.dp
    configuration.screenWidthDp.dp

    var password by remember { mutableStateOf("") }






    val orangeColor = colorResource(id = R.color.logo_orange)
    colorResource(id = R.color.logo_blue)
    val orangeLightColor = orangeColor.copy(TextFieldDefaults.BackgroundOpacity)




    val constraints = ConstraintSet {
        val backButton = createRefFor("backButton")
        val imageBox = createRefFor("imageBox")
        val userNameText = createRefFor("userNameText")
        val passwordField = createRefFor("passwordField")
        val loginButton = createRefFor("loginButton")
        constrain(backButton) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
        constrain(userNameText) {
            top.linkTo(imageBox.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(imageBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(passwordField) {
            top.linkTo(userNameText.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(backButton) {
            bottom.linkTo(loginButton.bottom)
            start.linkTo(passwordField.start)
        }
        constrain(loginButton) {
            top.linkTo(passwordField.bottom)
            end.linkTo(passwordField.end)
        }
    }
    ConstraintLayout(
        constraints,
        modifier = Modifier
            .width(400.dp)
            .height(300.dp)
    ) {
        Box() {
        }
        TextField(
            value = user?.fullName ?: "",
            label = { Text(text = "Username", color = orangeColor, fontSize = 16.sp) },
            enabled = false,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Account Icon",
                    tint = orangeLightColor
                )
            },
            textStyle = TextStyle(fontSize = 20.sp),
            onValueChange = {},
            modifier = Modifier
                .width(220.dp)
                .height(60.dp)
                .layoutId("userNameText"),
            colors = TextFieldDefaults.textFieldColors(
                textColor = orangeColor,
                backgroundColor = orangeLightColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = CutCornerShape(12.dp)
        )

        if(screenHeight>500.dp) {

            Image(
                modifier = Modifier
                    .layoutId("imageBox")
                    .padding(8.dp)
                    .fillMaxSize(0.7f),
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = "Profile Image",
                alignment = Alignment.Center
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .layoutId("passwordField")
                .width(220.dp)
                .height(60.dp)
                .offset(0.dp, 6.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = orangeColor,
                unfocusedBorderColor = orangeColor,
                cursorColor = orangeColor
            ),
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
            onValueChange = { password = it },
            label = {
                Text(
                    "Password",
                    color = colorResource(id = R.color.logo_orange),
                    fontSize = 20.sp
                )
            }

        )
        Button(
            onClick = onBackClicked, modifier = Modifier
                .height(55.dp)
                .width(105.dp)
                .layoutId("backButton")
                .offset(0.dp, 15.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = orangeLightColor,
                disabledContentColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            ),
            shape = CutCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back Icon",
                tint = orangeColor
            )
        }

        Button(
            onClick = {onLoginClicked(password)}, modifier = Modifier
                .height(55.dp)
                .width(105.dp)
                .layoutId("loginButton")
                .offset(0.dp, 15.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = orangeLightColor),
            shape = CutCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Login,
                contentDescription = "Login Icon",
                tint = orangeColor
            )
        }


    }


}

