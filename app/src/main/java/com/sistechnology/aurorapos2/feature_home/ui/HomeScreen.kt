package com.sistechnology.aurorapos2.feature_home.ui

import EditReceiptItemDialog
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.core.ui.components.Dialog
import com.sistechnology.aurorapos2.feature_home.domain.models.validation.ArticleValidation
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticleEvent
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticleGroupGrid
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticlesGrid
import com.sistechnology.aurorapos2.feature_home.ui.articles.components.EditArticleBox
import com.sistechnology.aurorapos2.feature_home.ui.bar_drawer.BarDrawerEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ButtonsRow
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptColumn
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.components.BasketsRow
import com.sistechnology.aurorapos2.feature_payment.ui.PaymentEvent
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val articlesState = viewModel.articlesState.value
    val receiptState = viewModel.receiptState.value


    var showEditReceiptDialog by remember { mutableStateOf(false) }
    var showEmptyListDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEditArticleBox by remember { mutableStateOf(false) }

    var showEditArticleErrorDialog by remember {mutableStateOf(false)}
    var showEditArticleSuccessDialog by remember {mutableStateOf(false)}

    var editArticleErrorMessage by remember { mutableStateOf("") }





    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        if (event.route != Screen.UsersScreen.route) {
                            popUpTo(Screen.HomeScreen.route)
                        }
                    }
                }
                is HomeScreenViewModel.UiEvent.EditArticle -> {
                    when (event.validation) {
                        ArticleValidation.Success -> {
                            viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleSuccessDialog)
                        }
                        ArticleValidation.MissingName -> viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleErrorDialog(
                            LocalContext.current
                        ArticleValidation.MissingPrice -> TODO()
                        ArticleValidation.InvalidName -> TODO()
                        ArticleValidation.InvalidPriceFormat -> TODO()
                        ArticleValidation.InvalidPriceRange -> TODO()
                    }
                }

            }
        }
    }

    Scaffold(
        topBar = {
            AppBar(
                onMenuDrawerClick = {},
                onLogoutClick = { showLogoutDialog = true },
                navController = navController
            )
        },
        content = { padding ->
            Surface(modifier = Modifier.padding(10.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.5f)
                        ) {
                            BasketsRow(
                                basketList = receiptState.receiptList,
                                onClick = { viewModel.onReceiptEvent(ReceiptEvent.SelectBasket(it)) },
                                getTotal = { viewModel.getCurrentBasketTotal(it) },
                                modifier = Modifier.weight(5.5f),
                                selectedBasketIndex = receiptState.selectedBasketIndex
                            )
                            Box(modifier = Modifier.weight(4.5f))
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(7f)
                        ) {
                            ReceiptColumn(
                                receipt = receiptState.receiptList[receiptState.selectedBasketIndex],
                                total = viewModel.getReceiptTotal(),
                                Modifier
                                    .fillMaxSize()
                                    .weight(5.5f)
                                    .padding(5.dp),
                                onItemClick = { index, item ->
                                    viewModel.onReceiptEvent(
                                        ReceiptEvent.SelectReceiptItem(
                                            index,
                                            item
                                        )
                                    ); showEditReceiptDialog = true
                                })
                            ArticlesGrid(
                                articlesList = articlesState.articleList,
                                Modifier
                                    .fillMaxSize()
                                    .weight(4.5f),
                                onArticleClick = {
                                    viewModel.onArticleEvent(ArticleEvent.AddToReceipt(it))
                                },
                                onArticleLongClick = {
                                    viewModel.onArticleEvent(ArticleEvent.SelectArticle(it))
                                    showEditArticleBox = true
                                })
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1.5f)
                        ) {
                            ButtonsRow(
                                Modifier
                                    .fillMaxSize()
                                    .weight(5.5f),
                                {
                                    if (viewModel.receiptItemList.isNotEmpty()) viewModel.onReceiptEvent(
                                        ReceiptEvent.PayReceipt
                                    )
                                },
                                {
                                    if (viewModel.receiptItemList.isNotEmpty()) showEmptyListDialog =
                                        true
                                },
                                {}
                            )
                            ArticleGroupGrid(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(4.5f),
                                articleGroupList = articlesState.articleGroupList,
                                onClick = {
                                    viewModel.onArticleEvent(
                                        ArticleEvent.SelectArticleGroup(it)
                                    );
                                },
                                selectedArticleGroupId = articlesState.selectedArticleGroupId
                            )
                        }
                    }
                }
            }
        })

    //Dialogs
    if (showEditReceiptDialog) {
        EditReceiptItemDialog(
            receiptItem = receiptState.selectedReceiptItem,
            onDismiss = { showEditReceiptDialog = false },
            onDeleteClicked = { viewModel.onReceiptEvent(ReceiptEvent.DeleteReceiptItem) },
            onSaveClicked = { viewModel.onReceiptEvent(ReceiptEvent.EditReceipt(it)) }
        )
    }
    if (showEmptyListDialog) {
        Dialog(
            confirmButtonText = stringResource(id = R.string.clear),
            messageText = stringResource(id = R.string.clear_receipt_confirm),
            titleText = stringResource(
                id = R.string.receipt
            ),
            onConfirm = { viewModel.onReceiptEvent(ReceiptEvent.ClearReceipt) },
            onDismiss = { showEmptyListDialog = false },
            confirmButtonColor = colorResource(id = R.color.delete_red),
            cancelButtonText = stringResource(id = R.string.cancel),
            imageVector = Icons.Default.Delete,
            imageColor = colorResource(id = R.color.delete_red)
        )
    }
    if (showLogoutDialog) {
        Dialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.confirm_logout_text),
            titleText = stringResource(id = R.string.logout),
            onConfirm = { viewModel.onBarDrawerEvent(BarDrawerEvent.LogoutEvent) },
            onDismiss = { showLogoutDialog = false },
            confirmButtonColor = colorResource(id = R.color.okay_button_green),
            cancelButtonText = stringResource(id = R.string.cancel),
            imageVector = Icons.Default.Logout

        )
    }
    if(articlesState.showEditArticleErrorDialog){
        Dialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = articlesState.editArticleErrorMessage,
            titleText = stringResource(id = R.string.edit_article),
            onConfirm = {viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleErrorDialog(""))},
            onDismiss = {viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleErrorDialog(""))},
            imageVector = Icons.Default.Error,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }
    if(articlesState.showEditArticleSuccessDialog){
        Dialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.edit_article_success),
            titleText = stringResource(id = R.string.edit_article),
            onConfirm = {viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleSuccessDialog)},
            onDismiss = {viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleSuccessDialog)},
            imageVector = Icons.Default.Done,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }



    AnimatedVisibility(
        visible = showEditArticleBox,
        enter = slideInVertically(initialOffsetY = { 600 }) + fadeIn(
            initialAlpha = 0.2f
        ),
        exit = slideOutVertically(targetOffsetY = { 600 }) + fadeOut(targetAlpha = 0.2f)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EditArticleBox(
                onDismiss = { showEditArticleBox = false },
                articleGroupList = articlesState.articleGroupList,
                articleInfo = articlesState.selectedArticleInfo,
                onSave = {}
            )
        }

    }


}