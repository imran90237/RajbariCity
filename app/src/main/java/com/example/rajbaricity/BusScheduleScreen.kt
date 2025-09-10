package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.rajbaricity.model.BusCounter
import com.example.rajbaricity.model.Bustime
import com.example.rajbaricity.ui.RajbariViewModel

// --- Main Screen with Tabs ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScheduleScreen(viewModel: RajbariViewModel) {
    // Fetch data when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.getBusCounters()
        viewModel.getBusTimes()
    }

    val tabTitles = listOf("🚌 কাউন্টার সমূহ", "🕐 সময়সূচী")
    var selectedTab by remember { mutableStateOf(0) }
    val busCounters by viewModel.busCounters.collectAsState()
    val busTimes by viewModel.busTimes.collectAsState()

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTab) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 20.sp, // 👈 Increase font size here
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // You can add actual content based on selectedTab here
            when (selectedTab) {
                0 -> CounterTabScreen(busCounters, viewModel) // 🚌 কাউন্টার সমূহ
                1 -> TimeScheduleTab(busTimes, viewModel)     // 🕐 সময়সূচী
            }
        }
    }
}


// --- COUNTER TAB ---
@Composable
fun CounterTabScreen(dynamicBusList: List<BusCounter>, viewModel: RajbariViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedBus by remember { mutableStateOf<BusCounter?>(null) }

    val staticBusList = remember {
        listOf(
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "পাংশা", contact = "01966274466"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "বাংলাদেশ হার্ট", contact = "01966274400"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "মাটিপাড়া", contact = "01400077304"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "বাণীবহ", contact = "01400522110"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "বহরপুর", contact = "01933799441"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "বালিয়াকান্দি", contact = "01909191555"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "নারুয়া", contact = "01946181118"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "গান্ধিমারা", contact = "01400567996"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "চন্দনি", contact = "01400556233"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "দরগাটোলা", contact = "01952530052"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "হাবাসপুর", contact = "01724822671"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "জামালপুর", contact = "01967737372"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "কালিবাড়ি", contact = "01709299767"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "নদুরিয়াঘাট", contact = "017160058957"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "বাহেরমোড়", contact = "01714210207"),
            BusCounter(counterName = "রাবেয়া পরিবহন", location = "বাহাদুরপুর", contact = "01713549552"),

            // সৌহার্দ্য পরিবহন
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "গাবতলী (ঢাকা)", contact = "01768235535"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "পাংশা", contact = "01718558338"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "রাজবাড়ী", contact = "01733167396"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "নদুরিয়াঘাট", contact = "01716005957"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "বাহেরমোড়", contact = "01713905113"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "জামালপুর", contact = "01729691558"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "বহরপুর", contact = "01736785093"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "বালিয়াকান্দি", contact = "01734626147"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "নারুয়া", contact = "01916723226"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "হাবাসপুর", contact = "01719799100"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "কালিবাড়ি", contact = "01825408210"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "বাণীবহ", contact = "01740909540"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "সেনগ্রাম", contact = "01726960435"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "মীরগী বাজার", contact = "01740849550"),
            BusCounter(counterName = "সৌহার্দ্য পরিবহন", location = "বাগডুলি", contact = "01724364707"),

            // গোল্ডেন লাইন পরিবহন
            BusCounter(counterName = "গোল্ডেন লাইন পরিবহন", location = "রাজবাড়ী", contact = "01711151864"),

            // জামান ইন্টারপ্রাইজ
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "পাংশা", contact = "01333662823"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "ঢাকা", contact = "01333390582"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "গান্ধিমারা", contact = "01826746959"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "কালুখালী", contact = "01126746954"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "সোনাপুর মোড়", contact = "01826746950"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "মাছপাড়া", contact = "01826746959"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "হাবাসপুর", contact = "01333390586"),
            BusCounter(counterName = "জামান ইন্টারপ্রাইজ", location = "সেনগ্রাম", contact = "01333662827"),

            // হানিফ এন্টারপ্রাইজ
            BusCounter(counterName = "হানিফ এন্টারপ্রাইজ", location = "রাজবাড়ী", contact = "01794594136"),

            // রাজবাড়ী পরিবহন সপ্তবর্ণা
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "রাজবাড়ী মালিক সমিতি", contact = "01907099021"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "পাংশা", contact = "01907099017"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "কালুখালী", contact = "01907099018"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "মাসপাড়া", contact = "01907099023"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "গান্ধিমারা", contact = "01907099019"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "হাবাসপুর", contact = "01724822671"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "বাহাদুরপুর", contact = "01713549552"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "মুরগি ফার্ম, রাজবাড়ী", contact = "01907099020"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "কালিতলা", contact = "01907099024"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "দরগাতলা", contact = "01975339218"),
            BusCounter(counterName = "রাজবাড়ী পরিবহন সপ্তবর্ণা", location = "উদয়পুর", contact = "01754417406")
        )
    }

    val combinedBusList = remember(staticBusList, dynamicBusList) {
        staticBusList + dynamicBusList
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("📍 রাজবাড়ীর বাস কাউন্টার", style = MaterialTheme.typography.titleLarge)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(combinedBusList) { bus ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedBus = bus },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("নাম: ${bus.counterName}", style = MaterialTheme.typography.titleMedium)
                            Text("স্থান: ${bus.location}", fontSize = 14.sp)
                            Text("যোগাযোগ: ${bus.contact}", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Bus")
        }

        if (showDialog) {
            AddBusDialog(
                onAdd = { name, location, contact ->
                    viewModel.addBusCounter(BusCounter(counterName = name, location = location, contact = contact))
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }

        selectedBus?.let {
            BusDetailsDialog(bus = it, onDismiss = { selectedBus = null })
        }
    }
}

// --- TIME SCHEDULE TAB ---
 @Composable
fun TimeScheduleTab(dynamicBusTimes: List<Bustime>, viewModel: RajbariViewModel) {
    var showDialog by remember { mutableStateOf(false) }

    val staticBusTimes = remember {
        listOf(
            Bustime(busName = "রাবেয়া", fromLocation = "রাজবাড়ী", toLocation = "ঢাকা", time = "৬:০০ AM", contact = "০১৭xxxxxxx"),
            Bustime(busName = "সৌহার্দ্য", fromLocation = "রাজবাড়ী", toLocation = "চুয়াডাঙ্গা", time = "৭:৩০ AM", contact = "০১৭xxxxxxx"),
            Bustime(busName = "গোল্ডেন লাইন", fromLocation = "রাজবাড়ী", toLocation = "চট্টগ্রাম", time = "৮:১৫ AM", contact = "০১৮xxxxxxx")
        )
    }

    val combinedBusTimes = remember(staticBusTimes, dynamicBusTimes) {
        staticBusTimes + dynamicBusTimes
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("বাস", fontSize = 16.sp, modifier = Modifier.weight(1f))
                Text("স্থান", fontSize = 16.sp, modifier = Modifier.weight(1f))
                Text("গন্তব্য", fontSize = 16.sp, modifier = Modifier.weight(1f))
                Text("সময়", fontSize = 16.sp, modifier = Modifier.weight(1f))
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                items(combinedBusTimes) { time ->
                    val context = LocalContext.current
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val phone = time.contact.replace("[^0-9+]".toRegex(), "")
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phone")
                                }
                                context.startActivity(intent)
                            }
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(time.busName, modifier = Modifier.weight(1f))
                        Text(time.fromLocation, modifier = Modifier.weight(1f))
                        Text(time.toLocation, modifier = Modifier.weight(1f))
                        Text(time.time, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Time")
        }

        if (showDialog) {
            AddTimeDialog(
                onAdd = { newTime ->
                    viewModel.addBusTime(newTime)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

// --- Add Bus Dialog ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBusDialog(
    onAdd: (String, String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && location.isNotBlank()) {
                    onAdd(name, location, contact)
                }
            }) {
                Text("✅ যোগ করুন")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("❌ বাতিল")
            }
        },
        title = { Text("🆕 নতুন বাস যুক্ত করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("বাসের নাম") }
                )
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("স্থান") }
                )
                OutlinedTextField(
                    value = contact,
                    onValueChange = { contact = it },
                    label = { Text("যোগাযোগ") }
                )
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}

// --- Add Time Dialog ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTimeDialog(
    onAdd: (Bustime) -> Unit,
    onDismiss: () -> Unit
) {
    var busName by remember { mutableStateOf("") }
    var from by remember { mutableStateOf("") }
    var to by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (busName.isNotBlank() && from.isNotBlank() && to.isNotBlank() && time.isNotBlank()) {
                    onAdd(Bustime(busName = busName, fromLocation = from, toLocation = to, time = time, contact = contact))
                }
            }) {
                Text("✅ Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("❌ Cancel")
            }
        },
        title = { Text("🆕 নতুন সময়সূচী") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = busName, onValueChange = { busName = it }, label = { Text("বাসের নাম") })
                OutlinedTextField(value = from, onValueChange = { from = it }, label = { Text("যাত্রা শুরুর স্থান") })
                OutlinedTextField(value = to, onValueChange = { to = it }, label = { Text("গন্তব্য") })
                OutlinedTextField(value = time, onValueChange = { time = it }, label = { Text("সময়") })
                OutlinedTextField(value = contact, onValueChange = { contact = it }, label = { Text("যোগাযোগ") })
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}

// --- Bus Details Dialog ---
@Composable
fun BusDetailsDialog(bus: BusCounter, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("📋 ${bus.counterName}") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val phone = bus.contact.replace("[^0-9+]".toRegex(), "")
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:$phone")
                            }
                            context.startActivity(intent)
                        }
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📍 ${bus.location}: ")
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(bus.contact, color = MaterialTheme.colorScheme.primary)
                }
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}