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

// --- Data Classes ---
data class BusSchedule(val name: String, val destinations: List<Pair<String, String>>)

data class BusTime(
    val busName: String,
    val from: String,
    val to: String,
    val time: String,
    val contact: String
)

// --- Main Screen with Tabs ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScheduleScreen() {
    val tabTitles = listOf("🚌 কাউন্টার সমূহ", "🕐 সময়সূচী")
    var selectedTab by remember { mutableStateOf(0) }

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
                0 -> CounterTabScreen() // 🚌 কাউন্টার সমূহ
                1 -> TimeScheduleTab()     // 🕐 সময়সূচী
            }
        }
    }
}


// --- COUNTER TAB ---
@Composable
fun CounterTabScreen() {
    var showDialog by remember { mutableStateOf(false) }
    var selectedBus by remember { mutableStateOf<BusSchedule?>(null) }

    val busList = remember {
        mutableStateListOf(
            BusSchedule(
                "রাবেয়া পরিবহনের কাউন্টার সমূহ", listOf(
                    "পাংশা" to "01966274466",
                    "বাংলাদেশ হার্ট" to "01966274400",
                    "মাটিপাড়া" to "01400077304",
                    "বাণীবহ" to "01400522110",
                    "বহরপুর" to "01933799441",
                    "বালিয়াকান্দি" to "01909191555",
                    "নারুয়া" to "01946181118",
                    "গান্ধিমারা" to "01400567996",
                    "চন্দনি" to "01400556233",
                    "দরগাটোলা" to "01952530052",
                    "হাবাসপুর" to "01724822671",
                    "জামালপুর" to "01967737372",
                    "কালিবাড়ি" to "01709299767",
                    "নদুরিয়াঘাট" to "017160058957",
                    "বাহেরমোড়" to "01714210207",
                    "বাহাদুরপুর" to "01713549552"
                )
            ),
            BusSchedule(
                "সৌহার্দ্য পরিবহনের কাউন্টার সমূহ", listOf(
                    "গাবতলী (ঢাকা)" to "01768235535",
                    "পাংশা" to "01718558338",
                    "রাজবাড়ী" to "01733167396",
                    "নদুরিয়াঘাট" to "01716005957",
                    "বাহেরমোড়" to "01713905113",
                    "জামালপুর" to "01729691558",
                    "বহরপুর" to "01736785093",
                    "বালিয়াকান্দি" to "01734626147",
                    "নারুয়া" to "01916723226",
                    "হাবাসপুর" to "01719799100",
                    "কালিবাড়ি" to "01825408210",
                    "বাণীবহ" to "01740909540",
                    "সেনগ্রাম" to "01726960435",
                    "মীরগী বাজার" to "01740849550",
                    "বাগডুলি" to "01724364707"
                )
            ),
            BusSchedule(
                "গোল্ডেন লাইন পরিবহনের কাউন্টার সমূহ (রাজবাড়ী ➝ চট্টগ্রাম, ঢাকা, কক্সবাজার)", listOf(
                    "রাজবাড়ী" to "01711151864"
                )
            ),
            BusSchedule(
                "জামান ইন্টারপ্রাইজ", listOf(
                    "পাংশা" to "01333662823",
                    "ঢাকা" to "01333390582",
                    "গান্ধিমারা" to "01826746959",
                    "কালুখালী" to "01126746954",
                    "সোনাপুর মোড়" to "01826746950",
                    "মাছপাড়া" to "01826746959",
                    "হাবাসপুর" to "01333390586",
                    "সেনগ্রাম" to "01333662827"
                )
            ),
            BusSchedule(
                "হানিফ এন্টারপ্রাইজ (রাজবাড়ী ➝ চট্টগ্রাম)", listOf(
                    "রাজবাড়ী" to "01794594136"
                )
            ),
            BusSchedule(
                "রাজবাড়ী পরিবহন সপ্তবর্ণা", listOf(
                    "রাজবাড়ী মালিক সমিতি" to "01907099021",
                    "পাংশা" to "01907099017",
                    "কালুখালী" to "01907099018",
                    "মাসপাড়া" to "01907099023",
                    "গান্ধিমারা" to "01907099019",
                    "হাবাসপুর" to "01724822671",
                    "বাহাদুরপুর" to "01713549552",
                    "মুরগি ফার্ম, রাজবাড়ী" to "01907099020",
                    "কালিতলা" to "01907099024",
                    "দরগাতলা" to "01975339218",
                    "উদয়পুর" to "01754417406"
                )
            )

        )
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
                items(busList) { bus ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedBus = bus },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("নাম: ${bus.name}", style = MaterialTheme.typography.titleMedium)
                            Text("স্টপেজ: ${bus.destinations.size}টি", fontSize = 14.sp)
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
                onAdd = { name, destinations ->
                    busList.add(BusSchedule(name, destinations))
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
fun TimeScheduleTab() {
    var showDialog by remember { mutableStateOf(false) }

    val busTimes = remember {
        mutableStateListOf(
            BusTime("রাবেয়া", "রাজবাড়ী", "ঢাকা", "৬:০০ AM", "০১৭xxxxxxx"),
            BusTime("সৌহার্দ্য", "রাজবাড়ী", "চুয়াডাঙ্গা", "৭:৩০ AM", "০১৭xxxxxxx"),
            BusTime("গোল্ডেন লাইন", "রাজবাড়ী", "চট্টগ্রাম", "৮:১৫ AM", "০১৮xxxxxxx")
        )
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
                items(busTimes) { time ->
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
                        Text(time.from, modifier = Modifier.weight(1f))
                        Text(time.to, modifier = Modifier.weight(1f))
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
                    busTimes.add(newTime)
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
    onAdd: (String, List<Pair<String, String>>) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var destinationText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && destinationText.isNotBlank()) {
                    val destinations = destinationText
                        .split(",")
                        .mapNotNull {
                            val parts = it.trim().split(":")
                            if (parts.size == 2) parts[0].trim() to parts[1].trim() else null
                        }
                    onAdd(name, destinations)
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
                    value = destinationText,
                    onValueChange = { destinationText = it },
                    label = { Text("স্টপেজ:নাম্বার, যেমন: পাংশা:017xxx,মাটিপাড়া:018xxx") }
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
    onAdd: (BusTime) -> Unit,
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
                    onAdd(BusTime(busName, from, to, time, contact))
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
fun BusDetailsDialog(bus: BusSchedule, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        },
        title = { Text("📋 ${bus.name}") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                bus.destinations.forEach { (location, contact) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val phone = contact.replace("[^0-9+]".toRegex(), "")
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:$phone")
                                }
                                context.startActivity(intent)
                            }
                            .padding(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("📍 $location: ")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(contact, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}
