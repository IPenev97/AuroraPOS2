package com.sistechnology.aurorapos2.feature_clients.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sistechnology.aurorapos2.core.ui.components.AppBar
import com.sistechnology.aurorapos2.feature_clients.ui.components.SearchTable

@Composable
fun ClientsScreen(
    navController: NavController,
    viewModel: ClientsScreenViewModel = hiltViewModel()
) {

    Surface {
        Column(modifier = Modifier.fillMaxSize())
        {
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {

            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(8f)){
             Column(modifier = Modifier
                 .fillMaxHeight()
                 .weight(1f)) {

             }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(8f)) {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)){
                        SearchTable()
                    }
                    Row(modifier =Modifier.fillMaxWidth().weight(7f)){

                    }

                }
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)) {

                }

            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)){

            }
        }
    }
}