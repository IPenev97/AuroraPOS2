package com.sistechnology.aurorapos2.feature_home.data.repositories

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ParkedReceiptDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ReceiptItemDao
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ParkedReceiptEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ReceiptItemEntity
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.Receipt
import com.sistechnology.aurorapos2.feature_home.domain.models.receipt.ReceiptItem
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository

class ReceiptRepositoryImpl(
    val receiptDao: ParkedReceiptDao,
    val receiptItemDao: ReceiptItemDao,
    val sharedPreferencesHelper: SharedPreferencesHelper
) : ReceiptRepository {

    private fun Receipt.toEntity(): ParkedReceiptEntity {
        return ParkedReceiptEntity(
            id = this.id,
            basketNumber = this.basketNumber,
            userId = sharedPreferencesHelper.getCurrentUserId() ?: 0,
            discountType = this.discountType,
            discountValue = this.discountValue
        )
    }

    private fun ParkedReceiptEntity.toModel(receiptItemsList: MutableList<ReceiptItem>): Receipt {
        return Receipt(
            id = this.id,
            basketNumber = this.basketNumber,
            receiptItemList = receiptItemsList,
            discountType = this.discountType,
            discountValue = this.discountValue
        )
    }

    private fun ReceiptItem.toEntity(receiptId: Int): ReceiptItemEntity {
        return ReceiptItemEntity(
            id = this.id,
            name = this.name,
            quantity = this.quantity,
            price = this.price,
            articleId = this.articleId,
            receiptId = receiptId,
            discountValue = this.discountValue,
            discountType = this.discountType
        )
    }

    private fun ReceiptItemEntity.toModel(): ReceiptItem {
        return ReceiptItem(
            id = this.id,
            name = this.name,
            quantity = this.quantity,
            price = this.price,
            articleId = this.articleId,
            discountType = this.discountType,
            discountValue = this.discountValue
        )
    }

    override suspend fun getAllReceiptsByUserId(userId: Int): MutableList<Receipt> {
            val output: MutableList<Receipt> = arrayListOf()
            receiptDao.getAllReceiptsWithReceiptItemsByUserId(userId).forEach { receiptWithReceiptItems ->
                val receipt = receiptWithReceiptItems.receipt
                output.add(receipt.toModel(receiptWithReceiptItems.receiptItems.map{it.toModel()}.toMutableList()))
            }
        return output
    }

    override suspend fun getReceiptByUserIdAndBasketNumber(userId: Int, basketId: Int): Receipt {
        TODO("Not yet implemented")
    }

    override suspend fun addReceipt(vararg receipt: Receipt) {
        val ids: LongArray = receiptDao.insert(*receipt.map{ it.toEntity()}.toTypedArray())
        receipt.forEachIndexed { i,r -> addReceiptItem(*r.receiptItemList.toTypedArray(), receiptId = ids[i].toInt())}
    }


    private  suspend fun addReceiptItem(vararg receiptItem: ReceiptItem, receiptId: Int) {
        receiptItemDao.insert(*receiptItem.map{it.toEntity(receiptId)}.toTypedArray())
    }

    override suspend fun deleteReceiptItem(vararg receiptItem: ReceiptItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteParkedReceiptItems(userId: Int) {
        getAllReceiptsByUserId(userId).forEach { receipt -> receiptItemDao.delete(*receipt.receiptItemList.map {it.toEntity(receipt.id)}.toTypedArray()) }
    }
}