package com.sistechnology.aurorapos2.di.modules

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserDao
import com.sistechnology.aurorapos2.feature_authentication.data.local.dao.UserTypeDao
import com.sistechnology.aurorapos2.feature_authentication.data.repositories.UserRepositoryImpl
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.ArticleGroupDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.article.VatGroupDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ParkedReceiptDao
import com.sistechnology.aurorapos2.feature_home.data.local.dao.receipt.ReceiptItemDao
import com.sistechnology.aurorapos2.feature_home.data.repositories.ArticlesRepositoryImpl
import com.sistechnology.aurorapos2.feature_home.data.repositories.ReceiptRepositoryImpl
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository
import com.sistechnology.aurorapos2.feature_payment.data.local.dao.PaymentTypeDao
import com.sistechnology.aurorapos2.feature_payment.data.repositories.PaymentRepositoryImpl
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import com.sistechnology.aurorapos2.feature_settings.data.local.dao.TerminalParameterDao
import com.sistechnology.aurorapos2.feature_settings.data.repositories.TerminalParameterRepositoryImpl
import com.sistechnology.aurorapos2.feature_settings.domain.repositories.TerminalParameterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(dao: UserDao, userTypeDao: UserTypeDao) : UserRepository {
        return UserRepositoryImpl(dao, userTypeDao)
    }

    @Singleton
    @Provides
    fun provideArticleRepository(articleDao: ArticleDao, articleGroupDao: ArticleGroupDao, vatGroupDao: VatGroupDao) : ArticleRepository{
        return ArticlesRepositoryImpl(articleDao, articleGroupDao, vatGroupDao)
    }

    @Singleton
    @Provides
    fun providePaymentRepository(paymentDao: PaymentTypeDao) : PaymentRepository{
        return PaymentRepositoryImpl(paymentDao)
    }

    @Singleton
    @Provides
    fun provideReceiptRepository(receiptDao: ParkedReceiptDao, receiptItemDao: ReceiptItemDao, sharedPreferencesHelper: SharedPreferencesHelper) : ReceiptRepository{
        return ReceiptRepositoryImpl(receiptDao, receiptItemDao, sharedPreferencesHelper)
    }

    @Singleton
    @Provides
    fun provideTerminalParameterRepository(terminalParameterDao: TerminalParameterDao) : TerminalParameterRepository {
        return TerminalParameterRepositoryImpl(terminalParameterDao)
    }


}