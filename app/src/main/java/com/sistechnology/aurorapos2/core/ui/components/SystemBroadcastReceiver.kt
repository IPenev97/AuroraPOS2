package com.sistechnology.aurorapos2.core.ui.components

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun SystemBroadcastReceiver(
    action: String,
    onSystemEvent:(intent: Intent?) -> Unit
) {
    val context  = LocalContext.current
    val currentOnSystemEvent by rememberUpdatedState(onSystemEvent)

    DisposableEffect(context, action){
        val intentFilter = IntentFilter(action)
        val broadcast = object : BroadcastReceiver(){
            override fun onReceive(p0: Context?, intent: Intent?) {
                currentOnSystemEvent(intent)
            }
        }
        context.registerReceiver(broadcast, intentFilter)

        onDispose {
            context.unregisterReceiver(broadcast)
        }
    }
}