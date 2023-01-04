package com.sistechnology.aurorapos2.feature_payment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.core.ui.components.Dialog
import com.sistechnology.aurorapos2.feature_payment.ui.components.PaymentTypesGrid
import com.sistechnology.aurorapos2.feature_payment.ui.components.PaymentsTable
import com.sistechnology.aurorapos2.feature_payment.ui.components.TotalRow

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentScreenViewModel = hiltViewModel()
) {

    val paymentState = viewModel.paymentState

    var payment by remember { mutableStateOf(paymentState.value.total.toString()) }

    LaunchedEffect(key1 = paymentState.value.totalPayed) {
        val amount: Double = paymentState.value.total - paymentState.value.totalPayed
        if (amount > 0) {
            payment = amount.toString()
        } else {
            payment = "0.0"
        }

    }





    Scaffold(
        topBar = {
            AppBar(
                onMenuDrawerClick = {},
                onLogoutClick = { },
                navController
            )
        },
        content = { padding ->
            Surface(modifier = Modifier.padding(padding)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(8f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(8f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(3f)
                                    .padding(10.dp)
                            ) {
                                Row(modifier = Modifier.weight(2f)) {
                                    TotalRow(
                                        total = paymentState.value.total,
                                        payment,
                                        onPaymentChange = { payment = it })

                                }
                                Row(modifier = Modifier.weight(8f)) {
                                    PaymentsTable(
                                        paymentsList = viewModel.enteredPaymentsList,
                                        onDeletePayment = {
                                            viewModel.onPaymentEvent(
                                                PaymentEvent.DeletePayment(it)
                                            )
                                        },
                                        total = paymentState.value.total,
                                        totalPayed = paymentState.value.totalPayed
                                    )
                                }

                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .padding(10.dp)
                            ) {
                                PaymentTypesGrid(
                                    paymentTypes = paymentState.value.paymentTypeList,
                                    onClick = {
                                        viewModel.onPaymentEvent(
                                            PaymentEvent.EnterPayment(
                                                it,
                                                payment
                                            )
                                        )
                                    })
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {

                        }

                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(10.dp)
                    ) {

                    }
                }
            }
        })
    if(paymentState.value.showAlreadyPayedDialog) {
        Dialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.payment_finished),
            titleText = stringResource(id = R.string.receipt),
            onConfirm = { viewModel.onPaymentEvent(PaymentEvent.ToggleAlreadyPayedDialog(false))},
            onDismiss = { viewModel.onPaymentEvent(PaymentEvent.ToggleAlreadyPayedDialog(false))},
            imageVector = Icons.Default.Payment,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }
}