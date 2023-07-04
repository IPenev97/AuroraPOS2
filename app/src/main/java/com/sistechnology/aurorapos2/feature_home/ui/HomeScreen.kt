package com.sistechnology.aurorapos2.feature_home.ui

import EditReceiptItemBox
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.core.ui.components.CustomDialog
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleInfo
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticleEvent
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticleGroupGrid
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticlesGrid
import com.sistechnology.aurorapos2.feature_home.ui.articles.components.EditArticleBox
import com.sistechnology.aurorapos2.feature_home.ui.articles.components.FavouriteArticlesButtons
import com.sistechnology.aurorapos2.feature_home.ui.bar_drawer.BarDrawerEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ButtonsRow
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptColumn
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.components.BasketsRow
import com.sistechnology.aurorapos2.feature_home.ui.receipt.components.DiscountBox
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {

    val articlesState = viewModel.articlesState.value
    val receiptState = viewModel.receiptState.value
    val barDrawerState = viewModel.barDrawerState.value








    LaunchedEffect(key1 = true) {
        viewModel.clearBasket()
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        if (event.route != Screen.UsersScreen.route) {
                            popUpTo(Screen.HomeScreen.route)
                        }
                    }
                }
            }
        }
    }


    Scaffold(
        topBar = {
            AppBar(
                onMenuDrawerClick = {},
                onLogoutClick = { viewModel.onBarDrawerEvent(BarDrawerEvent.ToggleLogoutDialog(true)) },
                navController = navController,
                onSettingsClick = {viewModel.onBarDrawerEvent(BarDrawerEvent.NavigateToSettings)}
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
                                .weight(1f)
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
                                .weight(8f)
                        ) {
                            ReceiptColumn(
                                receipt = receiptState.receiptList[receiptState.selectedBasketIndex],
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
                                    );
                                    viewModel.onReceiptEvent(
                                        ReceiptEvent.ToggleEditReceiptItemBox(
                                            true
                                        )
                                    )
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
                                })
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                        ) {
                            ButtonsRow(
                                Modifier
                                    .fillMaxSize()
                                    .weight(5.5f),
                                onPayClicked =
                                {
                                    if (viewModel.receiptItemList.isNotEmpty()) viewModel.onReceiptEvent(
                                        ReceiptEvent.PayReceipt
                                    )

                                },
                                onCancelClicked = {
                                    if (viewModel.receiptItemList.isNotEmpty()) viewModel.onReceiptEvent(
                                        ReceiptEvent.ToggleClearReceiptItemListDialog(true)
                                    )
                                },
                                onDiscountClicked = {
                                    viewModel.onReceiptEvent(
                                        ReceiptEvent.ToggleDiscountBox(
                                            true
                                        )
                                    )
                                }
                            )
                            FavouriteArticlesButtons(
                                onFavouriteClick = {
                                    viewModel.onArticleEvent(
                                        ArticleEvent.SelectArticleGroup(
                                            0
                                        )
                                    )
                                },
                                onRecentlySoldClicked = {
                                    viewModel.onArticleEvent(
                                        ArticleEvent.SelectArticleGroup(
                                            -1
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.5f),
                                selectedArticleGroupId = articlesState.selectedArticleGroupId
                            )
                            ArticleGroupGrid(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(4f),
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
    if (receiptState.showClearReceiptItemListDialog) {
        CustomDialog(
            confirmButtonText = stringResource(id = R.string.clear),
            messageText = stringResource(id = R.string.clear_receipt_confirm),
            titleText = stringResource(
                id = R.string.receipt
            ),
            onConfirm = { viewModel.onReceiptEvent(ReceiptEvent.ClearReceipt) },
            onDismiss = { viewModel.onReceiptEvent(ReceiptEvent.ToggleClearReceiptItemListDialog(false)) },
            confirmButtonColor = colorResource(id = R.color.delete_red),
            cancelButtonText = stringResource(id = R.string.cancel),
            imageVector = Icons.Default.Delete,
            imageColor = colorResource(id = R.color.delete_red)
        )
    }
    if (barDrawerState.showLogoutDialog) {
        CustomDialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.confirm_logout_text),
            titleText = stringResource(id = R.string.logout),
            onConfirm = { viewModel.onBarDrawerEvent(BarDrawerEvent.LogoutEvent) },
            onDismiss = { viewModel.onBarDrawerEvent(BarDrawerEvent.ToggleLogoutDialog(false)) },
            confirmButtonColor = colorResource(id = R.color.okay_button_green),
            cancelButtonText = stringResource(id = R.string.cancel),
            imageVector = Icons.Default.Logout

        )
    }
    if (articlesState.showEditArticleSuccessDialog) {
        CustomDialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.edit_article_success),
            titleText = stringResource(id = R.string.edit_article),
            onConfirm = { viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleSuccessDialog(false)) },
            onDismiss = { viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleSuccessDialog(false)) },
            imageVector = Icons.Default.Done,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }





    //Edit Boxes
    AnimatedVisibility(
        visible = articlesState.showEditArticleBox,
        enter = slideInVertically(initialOffsetY = { 600 }) + fadeIn(
            initialAlpha = 0.2f
        ),
        exit = slideOutVertically(targetOffsetY = { 600 }) + fadeOut(targetAlpha = 0.2f)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EditArticleBox(
                onDismiss = { viewModel.onArticleEvent(ArticleEvent.ToggleEditArticleBox(false)) },
                articleGroupList = articlesState.articleGroupList,
                articleInfo = articlesState.selectedArticleInfo ?: ArticleInfo(),
                onSave = { viewModel.onArticleEvent(ArticleEvent.EditArticle(it)) },
                vatGroupList = articlesState.vatGroupList,
                onNameEntered = { viewModel.onArticleEvent(ArticleEvent.NameEntered(it)) },
                onPriceEntered = { viewModel.onArticleEvent(ArticleEvent.PriceEntered(it)) }
            )
        }

    }
    AnimatedVisibility(
        visible = receiptState.showDiscountBox,
        enter = slideInVertically(initialOffsetY = { 600 }) + fadeIn(
            initialAlpha = 0.2f
        ),
        exit = slideOutVertically(targetOffsetY = { 600 }) + fadeOut(targetAlpha = 0.2f)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            DiscountBox(
                receiptInfo = receiptState.selectedReceiptInfo,
                onSave = {viewModel.onReceiptEvent(ReceiptEvent.SaveChangesToReceipt)},
                onDismiss = { viewModel.onReceiptEvent(ReceiptEvent.ToggleDiscountBox(false)) },
                onDiscountTypeChanged = { viewModel.onReceiptEvent(ReceiptEvent.ReceiptDiscountTypeChanged) },
                onDiscountEntered = {
                    viewModel.onReceiptEvent(
                        ReceiptEvent.ReceiptDiscountEntered(
                            it
                        )
                    )
                })
        }
    }

    AnimatedVisibility(
        visible = receiptState.showEditReceiptItemBox,
        enter = slideInVertically(initialOffsetY = { 600 }) + fadeIn(
            initialAlpha = 0.2f
        ),
        exit = slideOutVertically(targetOffsetY = { 600 }) + fadeOut(targetAlpha = 0.2f)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            EditReceiptItemBox(
                receiptItemInfo = receiptState.selectedReceiptItemInfo,
                onDismiss = { viewModel.onReceiptEvent(ReceiptEvent.ToggleEditReceiptItemBox(false)) },
                onDeleteClicked = { viewModel.onReceiptEvent(ReceiptEvent.DeleteReceiptItem) },
                onSaveClicked = { viewModel.onReceiptEvent(ReceiptEvent.SaveChangesToReceiptItem) },
                onQuantityEntered = {
                    viewModel.onReceiptEvent(
                        ReceiptEvent.ReceiptItemQuantityEntered(
                            it
                        )
                    )
                },
                onMinusPressed = { viewModel.onReceiptEvent(ReceiptEvent.ReceiptItemMinusPressed) },
                onPlusPressed = { viewModel.onReceiptEvent(ReceiptEvent.ReceiptItemPlusPressed) },
                onDiscountEntered = {
                    viewModel.onReceiptEvent(
                        ReceiptEvent.ReceiptItemDiscountEntered(
                            it
                        )
                    )
                },
                onDiscountTypeChanged = { viewModel.onReceiptEvent(ReceiptEvent.ReceiptItemDiscountTypeChanged) }

            )
        }
    }


}