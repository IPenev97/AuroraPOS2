package com.sistechnology.aurorapos2.di.modules

import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import com.sistechnology.aurorapos2.feature_authentication.domain.use_case.users.*
import com.sistechnology.aurorapos2.feature_clients.domain.repositories.ClientRepository
import com.sistechnology.aurorapos2.feature_clients.domain.use_case.ClientUseCases
import com.sistechnology.aurorapos2.feature_clients.domain.use_case.GetAllClientsUseCase
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository
import com.sistechnology.aurorapos2.feature_home.domain.use_case.articles.*
import com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer.BarDrawerUseCases
import com.sistechnology.aurorapos2.feature_home.domain.use_case.bar_drawer.LogoutUseCase
import com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt.*
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import com.sistechnology.aurorapos2.feature_payment.domain.use_case.GetPaymentTypes
import com.sistechnology.aurorapos2.feature_payment.domain.use_case.PaymentUseCases
import com.sistechnology.aurorapos2.feature_settings.domain.repositories.TerminalParameterRepository
import com.sistechnology.aurorapos2.feature_settings.domain.use_case.GetPrintingDeviceInfoUseCase
import com.sistechnology.aurorapos2.feature_settings.domain.use_case.SavePrintingDeviceInfoUseCase
import com.sistechnology.aurorapos2.feature_settings.domain.use_case.SettingsUseCases
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
            getArticlesByGroupUseCase = GetArticlesByGroupUseCase(articlesRepository),
            getArticleInfo = GetArticleInfoUseCase(articlesRepository),
            editArticle = EditArticleUseCase(articlesRepository),
            getVatGroupsUseCase = GetVatGroupsUseCase(articlesRepository),
            getFavouriteArticlesUseCase = GetFavouriteArticlesUseCase(articlesRepository)
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
            ),
            getReceiptItemInfoUseCase = GetReceiptItemInfoUseCase(
                receiptRepository
            ),
            getReceiptInfoUseCase = GetReceiptInfoUseCase()
        )
    }

    @Singleton
    @Provides
    fun provideSettingsUseCases(
        terminalParameterRepository: TerminalParameterRepository,
        sharedPreferencesHelper: SharedPreferencesHelper
    ): SettingsUseCases {
        return SettingsUseCases(
            getPrintingDeviceInfoUseCase = GetPrintingDeviceInfoUseCase(terminalParameterRepository),
            savePrintingDeviceInfoUseCase = SavePrintingDeviceInfoUseCase(
                terminalParameterRepository,
                sharedPreferencesHelper
            )
        )
    }

    @Singleton
    @Provides
    fun provideClientUseCases(
        clientRepository: ClientRepository
    ) : ClientUseCases {
        return ClientUseCases(getAllClientsUseCase = GetAllClientsUseCase(clientRepository))
    }
}