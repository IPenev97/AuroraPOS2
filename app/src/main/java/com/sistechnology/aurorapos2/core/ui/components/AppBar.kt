package com.sistechnology.aurorapos2.core.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.Screen

@Composable
fun AppBar(
    onMenuDrawerClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onSettingsClick: () -> Unit,
    navController: NavController,
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name), color = Color.White) },
        backgroundColor = colorResource(id = R.color.logo_blue),
        elevation = 5.dp,
        navigationIcon = {
            IconButton(onClick = onMenuDrawerClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.draw_menu),
                    tint = Color.White
                )
            }
        },
        actions = {

        })
}