package com.sistechnology.aurorapos2.core.utils


import com.sistechnology.aurorapos2.feature_authentication.domain.models.User
import com.sistechnology.aurorapos2.feature_authentication.domain.repositories.UserRepository
import com.sistechnology.aurorapos2.feature_home.domain.models.article.Article
import com.sistechnology.aurorapos2.feature_home.domain.models.article.ArticleGroup
import com.sistechnology.aurorapos2.feature_home.domain.models.article.VatGroup
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ArticleRepository
import com.sistechnology.aurorapos2.feature_home.domain.repositories.ReceiptRepository
import com.sistechnology.aurorapos2.feature_payment.domain.models.PaymentType
import com.sistechnology.aurorapos2.feature_payment.domain.repositories.PaymentRepository
import com.sistechnology.aurorapos2.feature_settings.data.local.entities.TerminalParameterEntity
import com.sistechnology.aurorapos2.feature_settings.domain.repositories.TerminalParameterRepository

class PresetDataHelper(
    val userRepository: UserRepository,
    val articleRepository: ArticleRepository,
    val paymentRepository: PaymentRepository,
    val receiptRepository: ReceiptRepository,
    val terminalParameterRepository: TerminalParameterRepository
) {

    suspend fun populateDatabase() {

        articleRepository.addArticleGroup(
            ArticleGroup(name = "Fruits"),
            ArticleGroup(name = "Vegetables"),
            ArticleGroup(name = "Bread"),
            ArticleGroup(name = "Group3"),
            ArticleGroup(name = "Group"),
            ArticleGroup(name = "Group2"),
            ArticleGroup(name = "Group3"),
            ArticleGroup(name = "Group4"),
            ArticleGroup(name = "Group6"),
            ArticleGroup(name = "Group55"),
            ArticleGroup(name = "Group8"),
            ArticleGroup(name = "Group8"),
            ArticleGroup(name = "Group9"),
            ArticleGroup(name = "Group9"),
            ArticleGroup(name = "Group9"),
            ArticleGroup(name = "Group9"),
            ArticleGroup(name = "Group9")

        )

        userRepository.addUser(
            User(username = "Ivo", password = "1234"),
            User(username = "Peshi", password = "1234"),
            User(username = "Sasho", password = "1234"),
            User(username = "Gergi", password = "1234"),
            User(username = "hello", password = "1234"),
            User(username = "klasier", password = "1234"),
            User(username = "kasier2", password = "1234"),
            User(username = "kasier23", password = "1234"),
            User(username = "kasier24", password = "1234"),
            User(username = "kasier25", password = "1234"),
            User(username = "kasier26", password = "1234"),
            User(username = "kasier27", password = "1234"),
        )
        articleRepository.insertVatGroup(
            VatGroup(group = "A", value = 20.0),
            VatGroup(group = "B", value = 10.0),
            VatGroup(group = "C", value = 30.0),
            VatGroup(group = "D", value = 0.0),
        )

        articleRepository.addArticle(
            Article(name = "Banana", price = 1.50, articleGroupId = 1, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 1, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 3, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 1, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 1, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 1, vatGroupId = 2),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 3, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 5, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 5, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 6, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 6, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 7, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 7, vatGroupId = 3),
            Article(name = "Banana", price = 1.50, articleGroupId = 7, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 8, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 9, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 10, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 10, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 9, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 5, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 6, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 4),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 4, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 3, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 3, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 2, vatGroupId = 1),
            Article(name = "Banana", price = 1.50, articleGroupId = 1, vatGroupId = 1),


            )
        paymentRepository.addPayment(
            PaymentType(name = "Cash"),
            PaymentType(name = "Card"),
            PaymentType(name = "Cheque"),
        )
        terminalParameterRepository.saveTerminalParameter(TerminalParameterEntity())

    }

}