package com.sistechnology.aurorapos2.feature_authentication.ui.users.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable

@Composable
fun UsersAlertDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    text: String
) {
    if(show){
        AlertDialog(onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirm)
                { Text(text = "OK") }
            },
            title = { Text(text = "User Authentication") },
            text = { Text(text = text) },)
    }

}