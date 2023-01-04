package com.sistechnology.aurorapos2.di.modules

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users.*
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.ArticlesUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.GetArticleGroupsUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.GetArticlesByGroupUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.GetArticlesUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer.BarDrawerUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer.LogoutUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt.GetParkedReceiptsUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt.ParkCurrentReceiptsUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt.ReceiptUseCases
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import com.sistechnology.aurorapos2.feature_payment.domain.use_case.GetPaymentTypes
import com.sistechnology.aurorapos2.feature_payment.domain.use_case.PaymentUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideUserUseCases(repository: UserRepository): UserUseCases {
        return UserUseCases(
            getUser = GetUser(repository),
            getUsers = GetUsers(repository),
            getUserTypes = GetUserTypes(repository),
            authenticateUser = AuthenticateUser(repository)
        )
    }

    @Singleton
    @Provides
    fun provideBarDrawerUseCases(sharedPreferencesHelper: SharedPreferencesHelper): BarDrawerUseCases {
        return BarDrawerUseCases(
            logoutUseCase = LogoutUseCase(sharedPreferencesHelper)
        )
    }

    @Singleton
    @Provides
    fun provideArticlesUseCases(articlesRepository: ArticleRepository): ArticlesUseCases {
        return ArticlesUseCases(
            getArticlesUseCase = GetArticlesUseCase(articlesRepository),
            getArticleGroupsUseCase = GetArticleGroupsUseCase(articlesRepository),
            getArticlesByGroupUseCase = GetArticlesByGroupUseCase(articlesRepository)
        )
    }

    @Singleton
    @Provides
    fun providePaymentUseCases(paymentRepository: PaymentRepository): PaymentUseCases {
        return PaymentUseCases(getPaymentTypes = GetPaymentTypes(paymentRepository))
    }

    @Singleton
    @Provides
    fun provideReceiptUseCases(
        receiptRepository: ReceiptRepository,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): ReceiptUseCases {
        return ReceiptUseCases(
            parkCurrentReceiptsUseCase = ParkCurrentReceiptsUseCase(
                receiptRepository,
                sharedPreferencesHelper
            ),
            getParkedReceiptsUseCase = GetParkedReceiptsUseCase(
                receiptRepository,
                sharedPreferencesHelper
            )
        )
    }
}