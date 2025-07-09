package com.example.rajbaricity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.rajbaricity.Navigation.AppNavigation
import com.example.rajbaricity.ui.theme.RajbariCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RajbariCityTheme {
                val navController = rememberNavController()
                val viewModel: RajbariViewModel = viewModel()

                // একবারে একটাই NavHost: AppNavigation()
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}
