package com.example.rajbaricity

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class TrainInfo(
    val trainName: String,
    val departureTime: String,
    val destination: String
)

data class Station(
    val name: String,
    val imageRes: Int,
    val trains: List<TrainInfo> = emptyList()
)

val stations = listOf(
    Station(
        "খানখানাপুর", R.drawable.khankhanapur,
        listOf(
            TrainInfo("নকশি কাঁথা কমিউটার (২৫)", "৫:৫১ AM", "ঢাকা (৯:১০ AM)"),
            TrainInfo("রাজবাড়ী মেইল-১ (১০১)", "৭:২১ AM", "ঢাকা (৮:৪৫ AM)"),
            TrainInfo("রাজবাড়ী মেইল-৩ (১০৫)", "৬:৩১ PM", "ভাঙ্গা (৭:৫০ PM)"),
            TrainInfo("নকশি কাঁথা কমিউটার (২৬)", "২:১৯ PM", "খুলনা (৯:০৫ PM)"),
            TrainInfo("রাজবাড়ী মেইল-২ (১০২)", "১০:৪২ AM", "কালুখালী জংশন (১১:৪৫ AM)"),
            TrainInfo("রাজবাড়ী মেইল-৪ (১০৬)", "৯:৩২ PM", "রাজবাড়ী (১০:০০ PM)")
        )
    ),

    Station(
        "রাজবাড়ী", R.drawable.rajbari,
        listOf(
            TrainInfo("সুন্দরবন এক্সপ্রেস (৭২৫)", "২:৪০ PM", "ঢাকা (৫:১০ AM পরদিন) - [মঙ্গলবার বন্ধ]"),
            TrainInfo("মধুমতি এক্সপ্রেস (৭৫৬)", "১১:০৫ AM", "ঢাকা (২:০০ PM) - [শনিবার বন্ধ]"),
            TrainInfo("বেনাপোল এক্সপ্রেস (৭৯৫)", "৫:৫০ PM", "ঢাকা (৮:৩০ PM) - [বুধবার বন্ধ]"),
            TrainInfo("নকশি কাঁথা কমিউটার (২৫)", "৫:৩০ AM", "ঢাকা (৯:১০ AM)"),
            TrainInfo("রাজবাড়ী মেইল-১ (১০১)", "৭:০০ AM", "ভাঙ্গা (৮:৪৫ AM)"),
            TrainInfo("রাজবাড়ী মেইল-৩ (১০৫)", "৬:১০ PM", "ভাঙ্গা (৭:৫০ PM)"),
            TrainInfo("লোকাল (৫১৩)", "৬:১৫ AM", "গোয়ালন্দ ঘাট (৭:০০ AM)"),
            TrainInfo("লোকাল (৫০৫)", "২:৪৫ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("সুন্দরবন এক্সপ্রেস (৭২৬)", "১০:২৫ AM", "খুলনা (৩:৪০ PM) - [বুধবার বন্ধ]"),
            TrainInfo("মধুমতি এক্সপ্রেস (৭৫৫)", "৬:০৫ PM", "রাজশাহী (১০:৩০ PM) - [শনিবার বন্ধ]"),
            TrainInfo("বেনাপোল এক্সপ্রেস (৭৯৬)", "২:০০ AM", "বেনাপোল (৭:০০ AM) - [বুধবার বন্ধ]"),
            TrainInfo("নকশি কাঁথা কমিউটার (২৬)", "৩:০০ PM", "খুলনা (৯:০৫ PM)"),
            TrainInfo("রাজবাড়ী মেইল-২ (১০২)", "১১:১১ AM", "কালুখালি জংশন (১১:৪৫ AM)"),
            TrainInfo("লোকাল (৫০৬)", "৮:১৫ AM", "পর্দা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল (৫০৮)", "৪:৩৫ PM", "ঢাকা (৭:১৫ PM)")
        )
    ),

    Station(
        "আরকান্দি", R.drawable.arkandi,
        listOf(
            TrainInfo("ভাটিয়াপাড়া মেইল-১ (১০৩)", "১২:৪১ PM", "ভাটিয়াপাড়া ঘাট (২:২৫ PM)"),
            TrainInfo("ভাটিয়াপাড়া মেইল-২ (১০৪)", "৪:২৯ PM", "কালুখালি (৫:০০ PM)")
        )
    ),

    Station(
        "বহরপুর", R.drawable.bohorpur,
        listOf(
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস (৭৮৪)", "৭:২৮ PM", "গোবরা (১০:১০ PM) - আন্তঃনগর, সোমবার বন্ধ"),
            TrainInfo("ভাটিয়াপাড়া মেইল-১ (১০৩)", "১২:৩১ PM", "ভাটিয়াপাড়া ঘাট (২:২৫ PM)"),
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস (৭৮৩)", "৮:৫৭ AM", "গোবরা (১:১৫ PM) - আন্তঃনগর, মঙ্গলবার বন্ধ"),
            TrainInfo("ভাটিয়াপাড়া মেইল-২ (১০৪)", "৪:৩৯ PM", "কালুখালি (৫:০০ PM)")
        )
    ),


    Station(
        "বসন্তপুর", R.drawable.bosontopur,
        listOf(
            TrainInfo("রাজবাড়ী মেইল ১ (১০১)", "৭:৩০ AM", "ভাঙ্গা (৮:৪৫ AM)"),
            TrainInfo("রাজবাড়ী মেইল ৩ (১০৫)", "৬:৪০ PM", "ভাঙ্গা (৭:৫০ PM)"),
            TrainInfo("রাজবাড়ী মেইল ২ (১০২)", "১০:৩৩ AM", "কালুখালি জংশন (১১:৪৫ AM)"),
            TrainInfo("রাজবাড়ী মেইল ৪ (১০৬)", "৯:২৩ PM", "রাজবাড়ী (১০:০০ PM)")
        )
    ),

    Station(
        "সূর্য নগর", R.drawable.surjanagar,
        listOf(
            TrainInfo("রাজবাড়ী মেইল ৩ (১০৫)", "৫:৫১ PM", "ভাঙ্গা (৭:৫০ PM)"),
            TrainInfo("লোকাল ৫০৫", "২:১২ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("লোকাল ৫০৭", "১০:০০ PM", "রাত (১০:২০ PM)"),
            TrainInfo("রাজবাড়ী মেইল ২ (১০২)", "১১:২১ AM", "কালুখালি (১১:৪৫ AM)"),
            TrainInfo("লোকাল ৫০৬", "৮:২৫ AM", "পর্দা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল ৫০৮", "৪:৪৫ PM", "পুরাদা জংশন (৭:১৫ PM)")
        )
    ),



    Station(
        "রামদিয়া", R.drawable.ramdia,
        listOf(
            TrainInfo("ভাটিয়াপাড়া মেইল ১ (১০৩)", "১২:২২ PM", "ভাটিয়াপাড়া ঘাট (২:৪৫ PM)"),
            TrainInfo("ভাটিয়াপাড়া মেইল ২ (১০৪)", "৪:৪৮ PM", "কালুখালি (৫:০০ PM)")
        )
    ),

    Station(
        "নালিয়া গ্রাম", R.drawable.naliagram,
        listOf(
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস (৭৮৪) - আন্তঃনগর", "৭:৪২ PM", "গোবরা (১০:১০ PM) [সোমবার বন্ধ]"),
            TrainInfo("ভাটিয়াপাড়া মেইল ১ (১০৩)", "১২:৫০ PM", "ভাটিয়াপাড়া ঘাট (২:২৫ PM)"),
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস (৭৮৩) - আন্তঃনগর", "৮:৪৩ AM", "রাজশাহী (১:১৫ PM) [মঙ্গলবার বন্ধ]"),
            TrainInfo("ভাটিয়াপাড়া মেইল ২ (১০৪)", "৪:২০ PM", "কালুখালি (৫:০০ PM)")
        )
    ),



    Station(
        "গোয়ালন্দ বাজার", R.drawable.goaland_bazar,
        listOf(
            TrainInfo("লোকাল ৫১৩", "৬:৪০ AM", "গোয়ালন্দ ঘাট (৭:০০ AM)"),
            TrainInfo("লোকাল ৫০৫", "৩:১৬ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("লোকাল ৫০৬", "৭:৪৭ AM", "পুরাদা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল ৫০৮", "৪:০৩ PM", "পুরাদা জংশন (৭:১৫ PM)")
        )
    ),

    Station(
        "গোয়ালন্দ ঘাট", R.drawable.goaland_ghat,
        listOf(
            TrainInfo("লোকাল ৫০৬", "৭:৩০ AM", "পুরাদা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল ৫০৮", "৩:৫০ PM", "পুরাদা জংশন (৭:১৫ PM)")
        )
    ),

    Station(
        "মাছপাড়া", R.drawable.machpara,
        listOf(
            TrainInfo("নকশি কাঁথা কমিউটার ২৫", "৪:২৯ AM", "ঢাকা (৯:১০ AM)"),
            TrainInfo("লোকাল ৫০৫", "১:২১ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("লোকাল ৫০৬", "৯:১৩ AM", "পুরাদা জংশন (১১:১৫ AM)"),
            TrainInfo("নকশি কাঁথা কমিউটার ২৬", "৩:৪৮ PM", "খুলনা (৯:০৫ PM)"),
            TrainInfo("লোকাল ৫০৭", "৯:১৪ PM", "রাজবাড়ী (১০:২০ PM)"),
            TrainInfo("লোকাল ৫০৮", "৫:৩০ PM", "পুরাদা জংশন (৭:১৫ PM)")
        )
    ),


    Station(
        "কালুখালী", R.drawable.kalukhali,
        listOf(
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস ৭৮৪ (সোমবার বন্ধ)", "৭:১২ PM", "গোবরা (১০:১০ PM)"),
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস ৭৮৩ (মঙ্গলবার বন্ধ)", "৯:১২ AM", "রাজশাহী (১:১৫ PM)"),
            TrainInfo("মধ্যবর্তী এক্সপ্রেস ৭৫৬ (শনিবার বন্ধ)", "১০:৩১ AM", "ঢাকা (২:০০ PM)"),
            TrainInfo("মধুমতি এক্সপ্রেস ৭৫৫ (শনিবার বন্ধ)", "৬:২৫ PM", "রাজশাহী (১০:৩০ PM)"),
            TrainInfo("নকশি কাঁথা কমিউটার ২৫", "৪:৫৩ AM", "ঢাকা (৯:১০ AM)"),
            TrainInfo("নকশি কাঁথা কমিউটার ২৬", "৩:২৬ PM", "খুলনা (৯:০৫ PM)"),
            TrainInfo("ভাটিয়াপাড়া মেইল ১/১০৩", "১২:১০ PM", "ভাটিয়াপাড়া ঘাট (২:২৫ PM)"),
            TrainInfo("রাজবাড়ী মেইল ৩/১০৫", "৫:৩০ PM", "ভাঙ্গা (৭:৫০ PM)"),
            TrainInfo("লোকাল ৫০৫", "১:৪৮ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("লোকাল ৫০৬", "৮:৪৯ AM", "পুরাদা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল ৫০৭", "৯:৩৭ PM", "রাজবাড়ী (১০:২০ PM)"),
            TrainInfo("লোকাল ৫০৮", "৫:১৪ PM", "পুরাদা জংশন (৭:১৫ PM)")
        )
    ),

    Station(
        "পাংশা", R.drawable.pangsha,
        listOf(
            TrainInfo("সুন্দরবন এক্সপ্রেস ৭২৫ (মঙ্গলবার বন্ধ)", "১:৫৩ AM", "ঢাকা (৫:১০ AM)"),
            TrainInfo("সুন্দরবন এক্সপ্রেস ৭২৬ (বুধবার বন্ধ)", "১১:০১ AM", "খুলনা (৩:৪০ PM)"),
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস ৭৮৪ (সোমবার বন্ধ)", "৭:০০ PM", "গোবরা (১০:১০ PM)"),
            TrainInfo("টুঙ্গিপাড়া এক্সপ্রেস ৭৮৩ (মঙ্গলবার বন্ধ)", "৯:২৩ AM", "রাজশাহী (১:১৫ PM)"),
            TrainInfo("মধুমতি এক্সপ্রেস ৭৫৬ (শনিবার বন্ধ)", "১০:২০ AM", "ঢাকা (২:০০ PM)"),
            TrainInfo("মধুমতি এক্সপ্রেস ৭৫৫ (শনিবার বন্ধ)", "৬:৩৭ PM", "রাজশাহী (১০:৩০ PM)"),
            TrainInfo("নকশি কাঁথা কমিউটার ২৫", "৪:৪১ AM", "ঢাকা (৯:১০ AM)"),
            TrainInfo("নকশি কাঁথা কমিউটার ২৬", "৩:৩৮ PM", "খুলনা (৯:০৫ PM)"),
            TrainInfo("লোকাল ৫০৫", "১:৩২ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("লোকাল ৫০৬", "৯:০২ AM", "পুরাদা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল ৫০৭", "৯:২৫ PM", "রাজবাড়ী (১০:২০ PM)"),
            TrainInfo("লোকাল ৫০৮", "৫:২৭ PM", "পুরাদা জংশন (৭:১৫ PM)")
        )
    ),


    Station(
        "বেলগাছি", R.drawable.belgachi,
        listOf(
            TrainInfo("নকশি কাঁথা কমিউটার ২৫", "৫:০৪ AM", "ঢাকা (৯:১০ AM)"),
            TrainInfo("রাজধানী মেইল ৩/১০৫", "৫:৪১ PM", "ভাঙ্গা (৭:৫০ PM)"),
            TrainInfo("লোকাল ৫০৫", "২:০০ PM", "গোয়ালন্দ ঘাট (৩:৩০ PM)"),
            TrainInfo("লোকাল ৫০৬", "৮:৩৬ AM", "পুরাদা জংশন (১১:১৫ AM)"),
            TrainInfo("লোকাল ৫০৭", "৯:৫০ PM", "রাজবাড়ী (১০:২০ PM)"),
            TrainInfo("লোকাল ৫০৮", "৪:৫৫ PM", "পুরাদা জংশন (৭:১৫ PM)"),
            TrainInfo("নকশি কাঁথা কমিউটার ২৬", "৩:১৫ PM", "খুলনা (৯:০৫ PM)"),
            TrainInfo("রাজবাড়ী মেল ২/১০২", "১১:৩১ AM", "কালুখালী (১১:৪৫ AM)")
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainScheduleScreen(navController: NavController) {
    var selectedStation by remember { mutableStateOf<Station?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (selectedStation == null) "রেলওয়ে স্টেশনসমূহ" else selectedStation!!.name) },
                navigationIcon = {
                    if (selectedStation != null) {
                        IconButton(onClick = { selectedStation = null }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (selectedStation == null) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(padding)
                    .padding(8.dp)
            ) {
                items(stations) { station ->
                    StationCard(station = station) {
                        selectedStation = station
                    }
                }
            }
        } else {
            StationDetail(station = selectedStation!!, modifier = Modifier.padding(padding))
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

@Composable
fun StationDetail(station: Station, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "${station.name} স্টেশনের বিস্তারিত ট্রেন সময়সূচীঃ",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (station.trains.isNotEmpty()) {
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