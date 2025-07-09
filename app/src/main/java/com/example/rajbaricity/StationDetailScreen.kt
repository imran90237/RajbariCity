package com.example.rajbaricity.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rajbaricity.model.getStationByName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StationDetailScreen(
    stationName: String,
    navController: NavHostController
) {
    val station = getStationByName(stationName)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stationName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "$stationName স্টেশনের বিস্তারিত ট্রেন সময়সূচীঃ",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (station != null && station.trains.isNotEmpty()) {
                station.trains.forEach { train ->
                    Text("🚆 ${train.trainName}", style = MaterialTheme.typography.bodyLarge)
                    Text("⏰ ছাড়ে: ${train.departureTime}")
                    Text("📍 গন্তব্য: ${train.destination}")
                    Spacer(modifier = Modifier.height(12.dp))
                }
            } else {
                Text("এই স্টেশনের ট্রেন তথ্য পাওয়া যায়নি।", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
