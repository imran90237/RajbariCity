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
fun CounterTabScreen(busList: List<BusCounter>, viewModel: RajbariViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedBus by remember { mutableStateOf<BusCounter?>(null) }

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
fun TimeScheduleTab(busTimes: List<Bustime>, viewModel: RajbariViewModel) {
    var showDialog by remember { mutableStateOf(false) }

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