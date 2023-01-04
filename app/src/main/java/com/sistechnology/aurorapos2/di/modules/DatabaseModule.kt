package com.sistechnology.aurorapos2.di.modules

import android.content.Context
import androidx.room.Room
import com.sistechnology.aurorapos2.core.data.local.Database
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserDao
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserTypeDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleGroupDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ParkedReceiptDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ReceiptItemDao
import com.sistechnology.aurorapos2.feature_payment.data.local.dao.PaymentTypeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : Database {
        return Room.databaseBuilder(
            context,
            Database::class.java,
            Database.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(database: Database) : UserDao{
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideUserTypeDao(database: Database) : UserTypeDao{
        return database.userTypeDao()
    }

    @Singleton
    @Provides
    fun provideArticleDao(database: Database) : ArticleDao {
        return database.articleDao()
    }

    @Singleton
    @Provides
    fun provideArticleGroupDao(database: Database) : ArticleGroupDao {
        return database.articleGroupDao()
    }

    @Singleton
    @Provides
    fun providePaymentDao(database: Database) : PaymentTypeDao {
        return database.paymentDao()
    }

    @Singleton
    @Provides
    fun provideReceiptDao(database: Database) : ParkedReceiptDao{
        return database.receiptDao()
    }
    @Singleton
    @Provides
    fun provideReceiptItemDao(database: Database) : ReceiptItemDao{
        return database.receiptItemDao()
    }


}