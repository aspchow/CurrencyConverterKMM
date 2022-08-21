package com.avinash.currencyconverterkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.avinash.currencyconverterkmm.android.screen.ConvertCurrencyScreen
import com.avinash.currencyconverterkmm.android.screen.NetworkLoader
import com.avinash.currencyconverterkmm.android.screen.Screen
import com.avinash.currencyconverterkmm.android.screen.SelectCurrency
import com.avinash.currencyconverterkmm.repository.Repository
import com.avinash.currencyconverterkmm.repository.local.LocalDataSource
import com.avinash.currencyconverterkmm.repository.remote.ApiRefreshChecker
import com.avinash.currencyconverterkmm.repository.remote.ApiService
import com.avinash.currencyconverterkmm.repository.remote.RemoteDataSource
import com.avinash.currencyconverterkmm.viewmodel.CurrencyViewModel
import com.avinash.currencyconverterkmm.android.theme.CurrencyConverterTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


class MainActivity : ComponentActivity() {

    val  viewModel: CurrencyViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        CoroutineScope(Dispatchers.IO).launch {

         viewModel.getCurrencyRateFromServer().collect()

        }



        setContent {
            CurrencyConverterTheme {

                val navController = rememberNavController()


                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = Screen.CONVERT_CURRENCY,
                    ) {
                        composable(route = Screen.CONVERT_CURRENCY) {
                            ConvertCurrencyScreen(navController = navController, viewModel)
                        }

                        dialog(
                            route = Screen.SELECT_CURRENCY,
                            dialogProperties = DialogProperties(dismissOnClickOutside = false)
                        ) {
                            SelectCurrency(navController = navController, viewModel)
                        }

                        dialog(
                            route = Screen.NETWORK_LOADER,
                            dialogProperties = DialogProperties(
                                dismissOnClickOutside = false,
                                dismissOnBackPress = false
                            )
                        ) {
                            NetworkLoader()
                        }

                    }


                }
            }
        }
    }
}
