package com.example.rajbaricity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.rajbaricity.Navigation.AppNavGraph
import com.example.rajbaricity.ui.RajbariViewModel
import com.example.rajbaricity.ui.theme.RajbariCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: RajbariViewModel = viewModel()
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            RajbariCityTheme(darkTheme = isDarkMode) {

                val navController = rememberNavController()

                // AppNavGraph ব্যবহার করা হচ্ছে এখানে
                AppNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
