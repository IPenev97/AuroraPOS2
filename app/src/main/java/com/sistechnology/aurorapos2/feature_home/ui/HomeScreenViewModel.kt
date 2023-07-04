package com.sistechnology.aurorapos2.feature_home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sistechnology.aurorapos2.App
import com.sistechnology.aurorapos2.core.domain.values.receipt.CurrentReceipt
import com.sistechnology.aurorapos2.core.fp_comm.Printer
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.utils.InputValidator
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.DiscountType
import com.sistechnology.aurorapos2.feature_home.domain.models.enums.validation.ReceiptValidation
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptInfo
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.ArticlesUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer.BarDrawerUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt.ReceiptUseCases
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticleEvent
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticlesState
import com.sistechnology.aurorapos2.feature_home.ui.bar_drawer.BarDrawerEvent
import com.sistechnology.aurorapos2.feature_home.ui.bar_drawer.BarDrawerState
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mu.KotlinLogging


import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val barDrawerUseCases: BarDrawerUseCases,
    private val articlesUseCase: ArticlesUseCases,
    private val receiptUseCases: ReceiptUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val logger = KotlinLogging.logger {}

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _articlesState = mutableStateOf(ArticlesState())
    val articlesState: State<ArticlesState> = _articlesState

    private val _receiptItemList = mutableStateListOf<ReceiptItem>()
    val receiptItemList: List<ReceiptItem> = _receiptItemList

    private val _receiptState = mutableStateOf(ReceiptState())
    val receiptState: State<ReceiptState> = _receiptState

    private val _barDrawerState = mutableStateOf(BarDrawerState())
    val barDrawerState: State<BarDrawerState> = _barDrawerState

    private val sharedPrefs = SharedPreferencesHelper.getInstance(App.context)


    private var getArticlesJob: Job? = null
    private var getArticleGroupsJob: Job? = null
    private var getVatGroupsJob: Job? = null
    private var parkCurrentReceiptsJob: Job? = null
    private var getParkedReceiptsJob: Job? = null


    init {
        getParkedReceipts()
        onArticleEvent(ArticleEvent.SelectArticleGroup(1))
    }

     fun clearBasket() {
      if(sharedPrefs.getClearBasket()==true) {
          onReceiptEvent(ReceiptEvent.ClearReceipt)
          sharedPrefs.setClearBasket(false)
      }

    }


    fun onBarDrawerEvent(event: BarDrawerEvent) {
        when (event) {
            is BarDrawerEvent.LogoutEvent -> {
                parkCurrentReceipts()
                _barDrawerState.value = barDrawerState.value.copy(showLogoutDialog = false)
                barDrawerUseCases.logoutUseCase()

                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.UsersScreen.route))
                }

            }
            is BarDrawerEvent.ToggleLogoutDialog -> {
                _barDrawerState.value = barDrawerState.value.copy(showLogoutDialog = event.show)
            }
            BarDrawerEvent.NavigateToSettings -> {
                parkCurrentReceipts()
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.SettingsScreen.route))
                }
            }
            else -> {

            }
        }
    }

    fun onArticleEvent(event: ArticleEvent) {
        when (event) {

            is ArticleEvent.AddToReceipt -> {
                if (!increaseReceiptItemQuantity(event.article.id)) {
                    logger.info { "Article added to current receipt: ${event.article.name}" }
                    _receiptItemList.add(
                        ReceiptItem(
                            name = event.article.name,
                            price = event.article.price,
                            quantity = 1.0,
                            articleId = event.article.id
                        )
                    )
                    saveChangesToBasketReceiptList()
                } else {
                    logger.info { "Added quantity to article: ${event.article.name}" }
                }
            }
            is ArticleEvent.SelectArticleGroup -> {
                getArticles(event.id)
                _articlesState.value = articlesState.value.copy(selectedArticleGroupId = event.id)
            }


            //Edit Article Events
            is ArticleEvent.SelectArticle -> {
                viewModelScope.launch {
                    _articlesState.value = articlesState.value.copy(
                        selectedArticleInfo = articlesUseCase.getArticleInfo(event.article),
                        showEditArticleBox = true
                    )
                }
            }
            is ArticleEvent.ToggleEditArticleBox -> {
                _articlesState.value = articlesState.value.copy(showEditArticleBox = event.show)
            }
            is ArticleEvent.EditArticle -> {
                viewModelScope.launch {
                    articlesUseCase.editArticle(event.articleInfo)
                    _articlesState.value = articlesState.value.copy(
                        showEditArticleSuccessDialog = true,
                        showEditArticleBox = false
                    )
                }
            }
            is ArticleEvent.ToggleEditArticleSuccessDialog -> {
                _articlesState.value = articlesState.value.copy(
                    showEditArticleSuccessDialog = event.show,
                    showEditArticleBox = false
                )
            }
            is ArticleEvent.NameEntered -> {
                val articleInfo = articlesState.value.selectedArticleInfo?.copy(
                    name = event.name,
                    nameError = InputValidator.validateArticleName(event.name.trim())
                )
                _articlesState.value = articlesState.value.copy(selectedArticleInfo = articleInfo)
            }
            is ArticleEvent.PriceEntered -> {

                val articleInfo = articlesState.value.selectedArticleInfo?.copy(
                    price = event.price,
                    priceError = InputValidator.validateArticlePrice(event.price.trim())
                )
                _articlesState.value = articlesState.value.copy(selectedArticleInfo = articleInfo)
            }
            else -> {}
        }
    }


    fun onReceiptEvent(event: ReceiptEvent) {
        when (event) {

            //Edit Receipt Item events
            is ReceiptEvent.ToggleEditReceiptItemBox -> {
                _receiptState.value = receiptState.value.copy(showEditReceiptItemBox = event.show)
            }
            is ReceiptEvent.SaveChangesToReceiptItem -> {
                saveChangesToReceiptItem()
                _receiptState.value = receiptState.value.copy(showEditReceiptItemBox = false)
            }
            is ReceiptEvent.SelectReceiptItem -> {
                _receiptState.value = receiptState.value.copy(
                    selectedReceiptItemInfo = receiptUseCases.getReceiptItemInfoUseCase(
                        _receiptItemList[event.receiptIndex]
                    ),
                    selectedReceiptItemIndex = event.receiptIndex,
                )
            }
            is ReceiptEvent.DeleteReceiptItem -> {
                _receiptItemList.removeAt(_receiptState.value.selectedReceiptItemIndex)
                saveChangesToBasketReceiptList()
                _receiptState.value = receiptState.value.copy(showEditReceiptItemBox = false)
            }
            is ReceiptEvent.ReceiptItemQuantityEntered -> {
                val receiptItemInfo =
                    receiptState.value.selectedReceiptItemInfo.copy(
                        quantity = event.quantity,
                        quantityError = InputValidator.validateReceiptItemQuantity(event.quantity)
                    )
                _receiptState.value =
                    receiptState.value.copy(selectedReceiptItemInfo = receiptItemInfo)

            }
            ReceiptEvent.ReceiptItemMinusPressed -> {
                var receiptItemInfo =
                    receiptState.value.selectedReceiptItemInfo
                if (receiptItemInfo.quantityError != ReceiptValidation.InvalidQuantityFormat) {
                    val quantity = (receiptItemInfo.quantity.toDouble() - 1).toString()
                    receiptItemInfo =
                        receiptItemInfo.copy(
                            quantity = quantity,
                            quantityError = InputValidator.validateReceiptItemQuantity(quantity)
                        )
                    _receiptState.value =
                        receiptState.value.copy(selectedReceiptItemInfo = receiptItemInfo)
                }
            }
            ReceiptEvent.ReceiptItemPlusPressed -> {

                var receiptItemInfo =
                    receiptState.value.selectedReceiptItemInfo
                if (receiptItemInfo.quantityError != ReceiptValidation.InvalidQuantityFormat) {
                    val quantity = (receiptItemInfo.quantity.toDouble() + 1).toString()
                    receiptItemInfo =
                        receiptItemInfo.copy(
                            quantity = quantity,
                            quantityError = InputValidator.validateReceiptItemQuantity(quantity)
                        )
                    _receiptState.value =
                        receiptState.value.copy(selectedReceiptItemInfo = receiptItemInfo)
                }
            }
            is ReceiptEvent.ReceiptItemDiscountEntered -> {
                val receiptItemInfo =
                    receiptState.value.selectedReceiptItemInfo.copy(discountValue = event.discount)

                _receiptState.value =
                    receiptState.value.copy(
                        selectedReceiptItemInfo = receiptItemInfo.copy(
                            discountValueError = InputValidator.validateReceiptDiscount(
                                event.discount,
                                receiptItemInfo.discountType,
                                receiptItemInfo.getTotalWithoutDiscount()
                            )
                        )
                    )
            }
            ReceiptEvent.ReceiptItemDiscountTypeChanged -> {
                var receiptItemInfo = receiptState.value.selectedReceiptItemInfo
                val newDiscountType =
                    if (receiptItemInfo.discountType == DiscountType.Percent) DiscountType.Sum else DiscountType.Percent
                receiptItemInfo = receiptItemInfo.copy(
                    discountType = newDiscountType,
                    discountValueError = InputValidator.validateReceiptDiscount(
                        receiptItemInfo.discountValue,
                        newDiscountType,
                        receiptItemInfo.getTotalWithoutDiscount()
                    )
                )
                _receiptState.value =
                    receiptState.value.copy(selectedReceiptItemInfo = receiptItemInfo)
            }


            //Receipt Events
            is ReceiptEvent.PayReceipt -> {
                setCurrentBonToPrint()
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.PaymentScreen.route))
                }
            }

            is ReceiptEvent.ToggleClearReceiptItemListDialog -> {
                _receiptState.value =
                    receiptState.value.copy(showClearReceiptItemListDialog = event.show)
            }
            is ReceiptEvent.SelectBasket -> {
                val receiptList: List<Receipt> = _receiptState.value.receiptList
                val basketIndex = _receiptState.value.selectedBasketIndex
                receiptList[basketIndex].receiptItemList = _receiptItemList.toList()
                _receiptState.value = receiptState.value.copy(
                    selectedBasketIndex = event.index,
                    receiptList = receiptList,
                )
                _receiptItemList.clear()
                _receiptItemList.addAll(_receiptState.value.receiptList[_receiptState.value.selectedBasketIndex].receiptItemList)
            }
            is ReceiptEvent.ParkReceipts -> {
                parkCurrentReceipts()
            }


            //Edit Receipt events
            is ReceiptEvent.ClearReceipt -> {
                _receiptState.value = receiptState.value.copy(
                    showClearReceiptItemListDialog = false,
                    selectedReceiptInfo = ReceiptInfo()
                )
                _receiptItemList.clear()
                saveChangesToBasketReceiptList()
                saveDiscountToCurrentBasket()
            }
            is ReceiptEvent.ToggleDiscountBox -> {
                val receipt: Receipt =
                    receiptState.value.receiptList[receiptState.value.selectedBasketIndex]
                _receiptState.value = receiptState.value.copy(
                    showDiscountBox = event.show,
                    selectedReceiptInfo = receiptUseCases.getReceiptInfoUseCase(receipt)
                )
            }
            is ReceiptEvent.SaveChangesToReceipt -> {
                saveDiscountToCurrentBasket()
                _receiptState.value = receiptState.value.copy(showDiscountBox = false)
            }


            is ReceiptEvent.ReceiptDiscountEntered -> {
                var receiptInfo =
                    receiptState.value.selectedReceiptInfo.copy(discountValue = event.discount)
                _receiptState.value = receiptState.value.copy(
                    selectedReceiptInfo = receiptInfo.copy(
                        discountValueError = InputValidator.validateReceiptDiscount(
                            event.discount,
                            receiptInfo.discountType,
                            receiptInfo.sumWithoutDiscount
                        )
                    )
                )
            }
            ReceiptEvent.ReceiptDiscountTypeChanged -> {
                var selectedReceiptInfo = receiptState.value.selectedReceiptInfo
                val newDiscountType =
                    if (selectedReceiptInfo.discountType == DiscountType.Percent) DiscountType.Sum else DiscountType.Percent
                if (selectedReceiptInfo.discountValueError != ReceiptValidation.InvalidDiscountFormat) {
                    selectedReceiptInfo = selectedReceiptInfo.copy(
                        discountType = newDiscountType,
                        discountValueError = InputValidator.validateReceiptDiscount(
                            selectedReceiptInfo.discountValue,
                            newDiscountType,
                            selectedReceiptInfo.getTotal()
                        )
                    )
                }
                _receiptState.value =
                    receiptState.value.copy(selectedReceiptInfo = selectedReceiptInfo)
            }
            else -> {}
        }
    }

    private fun setCurrentBonToPrint() {
        Printer.currentBon.listArticles = receiptItemList
    }


    private fun saveChangesToBasketReceiptList() {
        val receiptList: MutableList<Receipt> = _receiptState.value.receiptList.toMutableList()
        val index = _receiptState.value.selectedBasketIndex
        receiptList[index] = receiptList[index].copy(receiptItemList = _receiptItemList)
        _receiptState.value = receiptState.value.copy(receiptList = receiptList)
    }

    private fun saveDiscountToCurrentBasket() {
        val receiptList: List<Receipt> = _receiptState.value.receiptList
        val index = _receiptState.value.selectedBasketIndex
        var discountValue = _receiptState.value.selectedReceiptInfo.discountValue
        if (discountValue.isEmpty())
            discountValue = "0.0"
        receiptList[index].discountValue = discountValue.toDouble()
        receiptList[index].discountType = _receiptState.value.selectedReceiptInfo.discountType
        _receiptState.value = receiptState.value.copy(receiptList = receiptList)
    }


    private fun saveChangesToReceiptItem() {
        val receiptItemInfo = _receiptState.value.selectedReceiptItemInfo
        _receiptItemList[_receiptState.value.selectedReceiptItemIndex] =
            receiptItemList[_receiptState.value.selectedReceiptItemIndex].copy(
                quantity = receiptItemInfo.quantity.toDouble(),
                discountType = receiptItemInfo.discountType,
                discountValue = if (receiptItemInfo.discountValue.isEmpty()) 0.0 else receiptItemInfo.discountValue.toDouble()
            )
        saveChangesToBasketReceiptList()
    }

    private fun increaseReceiptItemQuantity(id: Int): Boolean {
        val index = _receiptItemList.indexOfFirst { receiptItem -> receiptItem.articleId == id }
        if (index == -1) return false
        _receiptItemList[index] =
            _receiptItemList[index].let { it.copy(quantity = it.quantity + 1) }
        saveChangesToBasketReceiptList()
        return true
    }


    private fun getArticles(groupId: Int) {

        getArticlesJob?.cancel()
        if (groupId == 0) {
            getArticlesJob = articlesUseCase.getFavouriteArticlesUseCase().onEach { articles ->
                _articlesState.value = articlesState.value.copy(articleList = articles)
            }.launchIn(viewModelScope)
        } else {
            getArticlesJob = articlesUseCase.getArticlesByGroupUseCase(groupId).onEach { articles ->
                _articlesState.value = articlesState.value.copy(articleList = articles)
            }.launchIn(viewModelScope)
        }

        getArticleGroupsJob?.cancel()
        getArticleGroupsJob = articlesUseCase.getArticleGroupsUseCase().onEach { articleGroups ->
            _articlesState.value = articlesState.value.copy(articleGroupList = articleGroups)
        }.launchIn(viewModelScope)

        getVatGroupsJob?.cancel()
        getVatGroupsJob = articlesUseCase.getVatGroupsUseCase().onEach { vatGroups ->
            _articlesState.value = articlesState.value.copy(vatGroupList = vatGroups)
        }.launchIn(viewModelScope)
    }

    private fun parkCurrentReceipts() {
        _receiptState.value.receiptList[receiptState.value.selectedBasketIndex].receiptItemList =
            _receiptItemList.toList()
        parkCurrentReceiptsJob?.cancel()
        parkCurrentReceiptsJob =
            viewModelScope.launch { receiptUseCases.parkCurrentReceiptsUseCase(*_receiptState.value.receiptList.toTypedArray()) }
    }

    private fun getParkedReceipts() {
        getParkedReceiptsJob?.cancel()
        getParkedReceiptsJob = viewModelScope.launch {
            _receiptState.value =
                receiptState.value.copy(receiptList = receiptUseCases.getParkedReceiptsUseCase())
            _receiptItemList.addAll(_receiptState.value.receiptList[0].receiptItemList)
        }

    }

    fun getCurrentBasketTotal(index: Int): Double {
        return _receiptState.value.receiptList[index].getTotal()
    }


    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
    }


}