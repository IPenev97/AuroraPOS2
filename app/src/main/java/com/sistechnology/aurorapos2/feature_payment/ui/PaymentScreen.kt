package com.sistechnology.aurorapos2.feature_payment.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Print
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.R
import com.sistechnology.aurorapos2.core.fp_comm.Fp_Error
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.core.ui.components.CustomDialog
import com.sistechnology.aurorapos2.core.ui.components.ProgressDialog
import com.sistechnology.aurorapos2.feature_home.ui.HomeScreenViewModel
import com.sistechnology.aurorapos2.feature_payment.ui.components.PaymentTypesGrid
import com.sistechnology.aurorapos2.feature_payment.ui.components.PaymentsTable
import com.sistechnology.aurorapos2.feature_payment.ui.components.TotalRow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentScreenViewModel = hiltViewModel(),
) {

    val paymentState = viewModel.paymentState

    var printErrorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(key1 = paymentState.value.errorPrintMessage){
        when (paymentState.value.errorPrintMessage){
            Fp_Error.NO_PAPER -> printErrorMessage = context.getString(R.string.error_printing_no_paper)
            Fp_Error.FP_NOT_READY -> printErrorMessage = context.getString(R.string.error_printing_not_ready)
            Fp_Error.NO_BATTERY ->  printErrorMessage = context.getString(R.string.error_printing_no_battery)
            else -> printErrorMessage = ""
        }
    }
    LaunchedEffect(key1 = true) {

        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeScreenViewModel.UiEvent.Navigate -> {
                    navController.popBackStack()
                }
            }
        }
    }













            Surface() {
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
                            ) {
                                Row(modifier = Modifier.weight(2.5f)) {
                                    TotalRow(
                                        total = paymentState.value.total,
                                        payment = paymentState.value.payment,
                                        paymentValidation = paymentState.value.paymentError,
                                        onPaymentEntered = {
                                            viewModel.onPaymentEvent(
                                                PaymentEvent.PaymentChanged(
                                                    it
                                                )
                                            )
                                        })

                                }
                                Row(modifier = Modifier.weight(7f)) {
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
                            ) {
                                PaymentTypesGrid(
                                    paymentTypes = paymentState.value.paymentTypeList,
                                    onClick = {
                                        viewModel.onPaymentEvent(
                                            PaymentEvent.EnterPayment(
                                                it
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
                    ) {

                    }
                }
            }

    if (paymentState.value.showAlreadyPayedDialog) {
        CustomDialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.payment_finished),
            titleText = stringResource(id = R.string.receipt),
            onConfirm = { viewModel.onPaymentEvent(PaymentEvent.ToggleAlreadyPayedDialog(false)) },
            onDismiss = { viewModel.onPaymentEvent(PaymentEvent.ToggleAlreadyPayedDialog(false)) },
            imageVector = Icons.Default.Payment,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }
    if(paymentState.value.showPrintingProgressDialog)
        ProgressDialog(
            title = stringResource(id = R.string.printing_process),
            onDismiss = { viewModel.onPaymentEvent(PaymentEvent.TogglePaymentProgressDialog(false)) })

    if (paymentState.value.showReceiptClosedDialog){
        CustomDialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = stringResource(id = R.string.receipt_closed),
            titleText = stringResource(id = R.string.receipt),
            onConfirm = {  viewModel.onPaymentEvent(PaymentEvent.FinishReceipt)},
            onDismiss = { viewModel.onPaymentEvent(PaymentEvent.TogglePrintCompleteDialog(false)) },
            imageVector = Icons.Default.Payment,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }

    if(paymentState.value.showPrintErrorDialog){
        CustomDialog(
            confirmButtonText = stringResource(id = R.string.ok),
            messageText = printErrorMessage,
            titleText = stringResource(id = R.string.receipt),
            onConfirm = { viewModel.onPaymentEvent(PaymentEvent.ToggleErrorPrintDialog(false))},
            onDismiss = { viewModel.onPaymentEvent(PaymentEvent.ToggleErrorPrintDialog(false))},
            imageVector = Icons.Default.Print,
            confirmButtonColor = colorResource(id = R.color.okay_button_green)
        )
    }
}