package com.sistechnology.aurorapos2.di.modules

import android.app.Application
import android.content.Context
import com.sistechnology.aurorapos2.core.utils.PresetDataHelper
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Singleton
    @Provides
    fun provideSharedPreferencesHelper(context: Context) : SharedPreferencesHelper{
        return SharedPreferencesHelper.getInstance(context)
    }

    @Singleton
    @Provides
    fun providePresetDataHelper(userRepository: UserRepository, articleRepository: ArticleRepository, paymentRepository: PaymentRepository, receiptRepository: ReceiptRepository) : PresetDataHelper{
        return PresetDataHelper(userRepository, articleRepository, paymentRepository, receiptRepository)
    }
}