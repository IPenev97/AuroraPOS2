package com.sistechnology.aurorapos2.feature_home.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sistechnology.aurorapos2.core.domain.values.receipt.CurrentReceiptList
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.ArticlesUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer.BarDrawerUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt.ReceiptUseCases
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticleEvent
import com.sistechnology.aurorapos2.feature_home.ui.articles.ArticlesState
import com.sistechnology.aurorapos2.feature_home.ui.bar_drawer.BarDrawerEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptEvent
import com.sistechnology.aurorapos2.feature_home.ui.receipt.ReceiptState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val barDrawerUseCases: BarDrawerUseCases,
    private val articlesUseCase: ArticlesUseCases,
    private val receiptUseCases: ReceiptUseCases
) : ViewModel() {


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _articlesState = mutableStateOf(ArticlesState())
    val articlesState: State<ArticlesState> = _articlesState

    private val _receiptItemList = mutableStateListOf<ReceiptItem>()
    val receiptItemList: List<ReceiptItem> = _receiptItemList

    private val _receiptState = mutableStateOf(ReceiptState())
    val receiptState: State<ReceiptState> = _receiptState


    private var getArticlesJob: Job? = null
    private var getArticleGroupsJob: Job? = null
    private var parkCurrentReceiptsJob: Job? = null
    private var getParkedReceiptsJob: Job? = null


    init {
        getParkedReceipts()
        onArticleEvent(ArticleEvent.SelectArticleGroup(1))
    }


    fun onBarDrawerEvent(event: BarDrawerEvent) {
        when (event) {
            is BarDrawerEvent.LogoutEvent -> {
                parkCurrentReceipts()
                barDrawerUseCases.logoutUseCase()
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.UsersScreen.route))
                }
            }
        }
    }

    fun onArticleEvent(event: ArticleEvent) {
        when (event) {
            is ArticleEvent.AddToReceipt -> {
                if (!increaseReceiptItemQuantity(event.article.id)) {
                    _receiptItemList.add(
                        ReceiptItem(
                            name = event.article.name,
                            price = event.article.price,
                            quantity = 1,
                            articleId = event.article.id
                        )
                    )
                    saveChangesToCurrentBasket()
                }
            }
            is ArticleEvent.SelectArticleGroup -> {
                getArticles(event.id)
                _articlesState.value = articlesState.value.copy(selectedArticleGroupId = event.id)
            }
            is ArticleEvent.SelectArticle -> {
                _articlesState.value = articlesState.value.copy(selectedArticle = event.article)
            }
        }
    }

    private fun saveChangesToCurrentBasket() {
        val receiptList: List<Receipt> = _receiptState.value.receiptList
        val basketIndex = _receiptState.value.selectedBasketIndex
        receiptList[basketIndex].receiptItemList = _receiptItemList.toList()
        _receiptState.value = receiptState.value.copy(receiptList = receiptList)
    }

    fun onReceiptEvent(event: ReceiptEvent) {
        when (event) {
            is ReceiptEvent.EditReceipt -> {
                var quantity = event.receiptItem.quantity
                if (quantity < 1) quantity = 1
                _receiptItemList[_receiptState.value.selectedReceiptIndex] =
                    receiptItemList[_receiptState.value.selectedReceiptIndex].copy(quantity = quantity)
                saveChangesToCurrentBasket()
            }
            is ReceiptEvent.SelectReceiptItem -> {
                _receiptState.value = receiptState.value.copy(
                    selectedReceiptItem = event.receiptItem,
                    selectedReceiptIndex = event.receiptIndex
                )
            }
            is ReceiptEvent.DeleteReceiptItem -> {
                _receiptItemList.removeAt(_receiptState.value.selectedReceiptIndex)
                saveChangesToCurrentBasket()
            }
            is ReceiptEvent.PayReceipt -> {
                CurrentReceiptList.currentReceipt = CurrentReceiptList.currentReceipt.copy(receiptItemList = _receiptItemList)
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Navigate(Screen.PaymentScreen.route))
                }

            }
            is ReceiptEvent.ClearReceipt -> {
                _receiptItemList.clear()
                saveChangesToCurrentBasket()
            }
            is ReceiptEvent.SelectBasket -> {
                val receiptList: List<Receipt> = _receiptState.value.receiptList
                val basketIndex = _receiptState.value.selectedBasketIndex
                receiptList[basketIndex].receiptItemList = _receiptItemList.toList()


                _receiptState.value = receiptState.value.copy(selectedBasketIndex = event.index, receiptList = receiptList)

                _receiptItemList.clear()
                _receiptItemList.addAll(_receiptState.value.receiptList[_receiptState.value.selectedBasketIndex].receiptItemList)
            }
            is ReceiptEvent.ParkReceipts -> {
                parkCurrentReceipts()
            }

        }
    }

    fun increaseReceiptItemQuantity(id: Int): Boolean {
        val index = _receiptItemList.indexOfFirst { receiptItem -> receiptItem.articleId == id }
        if (index == -1) return false
        _receiptItemList[index] = _receiptItemList[index].let { it.copy(quantity = it.quantity + 1) }
        saveChangesToCurrentBasket()
        return true
    }


    sealed class UiEvent {
        data class Navigate(val route: String) : UiEvent()
    }

    private fun getArticles(groupId: Int) {

        getArticlesJob?.cancel()
        getArticlesJob = articlesUseCase.getArticlesByGroupUseCase(groupId).onEach { articles ->
            _articlesState.value = articlesState.value.copy(articleList = articles)
        }.launchIn(viewModelScope)

        getArticleGroupsJob?.cancel()
        getArticleGroupsJob = articlesUseCase.getArticleGroupsUseCase().onEach { articleGroups ->
            _articlesState.value = articlesState.value.copy(articleGroupList = articleGroups)
        }.launchIn(viewModelScope)
    }

    private fun parkCurrentReceipts(){
        _receiptState.value.receiptList[receiptState.value.selectedBasketIndex].receiptItemList = _receiptItemList.toList()
        parkCurrentReceiptsJob?.cancel()
        parkCurrentReceiptsJob = viewModelScope.launch {receiptUseCases.parkCurrentReceiptsUseCase(*_receiptState.value.receiptList.toTypedArray())}
    }
    private fun getParkedReceipts(){
        getParkedReceiptsJob?.cancel()
        getParkedReceiptsJob = viewModelScope.launch { _receiptState.value = receiptState.value.copy(receiptList = receiptUseCases.getParkedReceiptsUseCase())
            _receiptItemList.addAll(_receiptState.value.receiptList[0].receiptItemList)
        }

    }

    fun getReceiptTotal(): Double {
        var total = 0.0
        for (item: ReceiptItem in receiptItemList) {
            total += item.quantity * item.price
        }
        return total
    }

    fun getCurrentBasketTotal(index: Int): Double{
        return _receiptState.value.receiptList[index].getTotal()
    }


}