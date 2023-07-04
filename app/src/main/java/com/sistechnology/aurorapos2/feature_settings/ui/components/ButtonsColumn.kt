package com.sistechnology.aurorapos2.feature_settings.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sistechnology.aurorapos2.R

@Composable
fun ButtonsColumn(
    onPrintingDeviceClick: () -> Unit,
    onGeneralSettingsClick: () -> Unit,
    onScaleDeviceClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SettingsButton(
            drawableId = R.drawable.ic_info,
            onClick = onGeneralSettingsClick,
            text = stringResource(id = R.string.general_settings)
        )
        SettingsButton(
            drawableId = R.drawable.ic_printing_device,
            onClick = onPrintingDeviceClick,
            text = stringResource(id = R.string.printing_device)
        )
        SettingsButton(
            drawableId = R.drawable.ic_scale_device,
            onClick = onScaleDeviceClick,
            text = stringResource(id = R.string.scale_device)
        )
    }
}