package com.example.rajbaricity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rajbaricity.model.Station


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainScheduleScreen(navController: NavController) {
    val stations = listOf(
        Station("পাংশা", R.drawable.pangsha),
        Station("কালুখালী", R.drawable.kalukhali),
        Station("রাজবাড়ী", R.drawable.rajbari),
        Station("গোয়ালন্দ ঘাট", R.drawable.goaland_ghat),
        Station("খানখানাপুর", R.drawable.khankhanapur),
        Station("আরকান্দি", R.drawable.arkandi),
        Station("বহরপুর", R.drawable.bohorpur),
        Station("বসন্তপুর", R.drawable.bosontopur),
        Station("সূর্য নগর", R.drawable.surjanagar),
        Station("রামদিয়া", R.drawable.ramdia),
        Station("নালিয়া গ্রাম", R.drawable.naliagram),
        Station("গোয়ালন্দ বাজার", R.drawable.goaland_bazar),
        Station("মাছপাড়া", R.drawable.machpara),
        Station("বেলগাছি", R.drawable.belgachi)
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("রেলওয়ে স্টেশনসমূহ") })
        }
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            items(stations) { station ->
                StationCard(station = station) {
                    navController.navigate("station_detail/${station.name}")
                }
            }
        }
    }
}

@Composable
fun StationCard(station: Station, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(180.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = station.imageRes),
                contentDescription = station.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = station.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
