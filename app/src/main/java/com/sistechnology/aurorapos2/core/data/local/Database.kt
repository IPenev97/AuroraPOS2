package com.sistechnology.aurorapos2.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserDao
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserTypeDao
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserEntity
import com.sistechnology.aurorapos2.feature_authentication.data.local.entities.UserTypeEntity
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleGroupDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ParkedReceiptDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ReceiptItemDao
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.ArticleEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.article.ArticleGroupEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ParkedReceiptEntity
import com.sistechnology.aurorapos2.feature_home.data.local.entities.receipt.ReceiptItemEntity
import com.sistechnology.aurorapos2.feature_payment.data.local.dao.PaymentTypeDao
import com.sistechnology.aurorapos2.feature_payment.data.local.entities.PaymentTypeEntity

@Database(
    entities = [
        UserEntity::class,
        UserTypeEntity::class,
        ArticleEntity::class,
        ArticleGroupEntity::class,
        PaymentTypeEntity::class,
        ParkedReceiptEntity::class,
        ReceiptItemEntity::class
               ],
    version = 16,
    exportSchema = true
)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userTypeDao(): UserTypeDao
    abstract fun articleDao(): ArticleDao
    abstract fun articleGroupDao(): ArticleGroupDao
    abstract fun paymentDao(): PaymentTypeDao
    abstract fun receiptDao(): ParkedReceiptDao
    abstract fun receiptItemDao() : ReceiptItemDao

    companion object {
        val DATABASE_NAME: String = "aurora.db"
    }


}
