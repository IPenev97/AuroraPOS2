package com.sistechnology.aurorapos2.feature_authentication.ui.users

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.feature_authentication.ui.users.components.UserAuthenticationBox
import com.sistechnology.aurorapos2.feature_authentication.ui.users.components.UserBox
import com.sistechnology.aurorapos2.feature_authentication.ui.users.components.UserBoxPortrait
import com.sistechnology.aurorapos2.feature_authentication.ui.users.components.UsersAlertDialog
import kotlinx.coroutines.flow.collectLatest


@Composable
fun UsersScreen(
    navController: NavController,
    viewModel: UsersScreenViewModel = hiltViewModel(),
) {


    val state = viewModel.state.value
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val constraints = ConstraintSet {
        val profilesGrid = createRefFor("profilesGrid")

        constrain(profilesGrid) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)

        }

    }


    SideEffect {
        if (screenHeight < 400.dp && state.selectedUser == null) {
            viewModel.onEvent(UserEvent.ToggleLogo(false))
        } else {
            viewModel.onEvent(UserEvent.ToggleLogo(true))
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UsersScreenViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route)
                }
            }
        }
    }

    UsersAlertDialog(
        show = state.showDialog,
        onDismiss = { viewModel.onEvent(UserEvent.ToggleDialog(false)) },
        onConfirm = { viewModel.onEvent(UserEvent.ToggleDialog(false)) },
        text = state.dialogMessage
    )






    Scaffold(
        topBar = {
            AppBar(
                onMenuDrawerClick = { },
                onLogoutClick = {},
                navController = navController,
                onSettingsClick = {}
            )
        },
        content = { padding ->
            ConstraintLayout(
                constraints, modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                AnimatedVisibility(visible = state.logoVisibility) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),


                        contentAlignment = Alignment.TopCenter

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logotitle),

                            contentDescription = "Aurora Logo"
                        )
                    }
                }



                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        AnimatedVisibility(
                            modifier = Modifier.layoutId("profilesGrid"),
                            visible = state.selectedUser == null,
                            enter = slideInVertically(initialOffsetY = { -600 }) + fadeIn(
                                initialAlpha = 0.2f
                            ),
                            exit = slideOutVertically(targetOffsetY = { -600 }) + fadeOut(
                                targetAlpha = 0.2f
                            )
                        ) {


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.6f)
                                    .offset(0.dp, (-100).dp),
                                contentAlignment = Alignment.Center
                            ) {
                                LazyHorizontalGrid(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .offset(30.dp),
                                    rows = GridCells.Adaptive(minSize = 210.dp),
                                    horizontalArrangement = Arrangement.Center,


                                    ) {
                                    items(state.userList) { user ->
                                        UserBox(user = user, onClick = {
                                            viewModel.onEvent(UserEvent.SelectUser(user))

                                        })

                                    }


                                }
                            }

                        }

                        AnimatedVisibility(
                            visible = state.selectedUser != null,
                            enter = slideInVertically(initialOffsetY = { 600 }) + fadeIn(
                                initialAlpha = 0.2f
                            ),
                            exit = slideOutVertically(targetOffsetY = { 600 }) + fadeOut(targetAlpha = 0.2f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(0.dp, -(30.dp)), contentAlignment = Alignment.Center
                            ) {
                                UserAuthenticationBox(
                                    user = state.selectedUser,
                                    onBackClicked = {
                                        viewModel.onEvent(UserEvent.SelectUser(null))
                                    },
                                    onLoginClicked = {
                                        viewModel.onEvent(
                                            UserEvent.Login(
                                                state.selectedUser?.username ?: "", "1234"
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                    else -> {
                        AnimatedVisibility(
                            modifier = Modifier.layoutId("profilesGrid"),
                            visible = state.selectedUser == null,
                            enter = slideInHorizontally(initialOffsetX = { -600 }) + fadeIn(
                                initialAlpha = 0.2f
                            ),
                            exit = slideOutHorizontally(targetOffsetX = { -600 }) + fadeOut(
                                targetAlpha = 0.2f
                            )
                        ) {


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.9f),
                            ) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {
                                    items(state.userList) { user ->
                                        UserBoxPortrait(user = user, onClick = {
                                            viewModel.onEvent(UserEvent.SelectUser(user))
                                        })

                                    }
                                }
                            }
                        }
                        AnimatedVisibility(
                            visible = state.selectedUser != null,
                            enter = slideInHorizontally(initialOffsetX = { 600 }) + fadeIn(
                                initialAlpha = 0.2f
                            ),
                            exit = slideOutHorizontally(targetOffsetX = { 600 }) + fadeOut(
                                targetAlpha = 0.2f
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(0.dp, 30.dp), contentAlignment = Alignment.TopCenter
                            ) {
                                UserAuthenticationBox(
                                    user = state.selectedUser,
                                    onBackClicked = {
                                        viewModel.onEvent(UserEvent.SelectUser(null))
                                    },
                                    onLoginClicked = {
                                        viewModel.onEvent(
                                            UserEvent.Login(
                                                state.selectedUser?.username ?: "", it
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }

                }





                when (configuration.orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {

                    }
                    else -> {

                    }

                }
            }
        })


}