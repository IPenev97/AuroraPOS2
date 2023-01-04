package com.sistechnology.aurorapos2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sistechnology.aurorapos2.core.ui.Screen
import com.sistechnology.aurorapos2.core.ui.theme.AuroraPOS2Theme
import com.sistechnology.aurorapos2.core.utils.SharedPreferencesHelper
import com.sistechnology.aurorapos2.feature_authentication.ui.users.UsersScreen
import com.sistechnology.aurorapos2.feature_home.ui.HomeScreen
import com.sistechnology.aurorapos2.feature_payment.ui.PaymentScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AuroraPOS2Theme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    val startDestination = if(sharedPreferencesHelper.getCurrentUserName()?.isEmpty()==true)Screen.UsersScreen.route else Screen.HomeScreen.route
                    NavHost(navController = navController, startDestination = startDestination){
                        composable(route = Screen.HomeScreen.route){
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.UsersScreen.route){
                            UsersScreen(navController = navController)
                        }
                        composable(route = Screen.PaymentScreen.route){
                            PaymentScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
@Preview()
@Composable
fun DefaultPreview() {

}