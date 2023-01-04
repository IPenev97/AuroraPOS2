package com.sistechnology.aurorapos2.feature_home.domain.use_case.receipt

data class ReceiptUseCases(
    val parkCurrentReceiptsUseCase: ParkCurrentReceiptsUseCase,
    val getParkedReceiptsUseCase: GetParkedReceiptsUseCase
)