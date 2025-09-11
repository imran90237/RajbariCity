package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rajbaricity.model.CarInfo
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun CarRentScreen(viewModel: RajbariViewModel = viewModel()) {
    val carList by viewModel.cars.collectAsState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("অ্যাম্বুলেন্স", "প্রাইভেট কার", "মাইক্রোবাস", "পিক আপ", "ট্রাক")
    var showAddDialog by remember { mutableStateOf(false) }

    val filteredList by remember(carList, selectedTabIndex) {
        derivedStateOf {
            carList.filter { it.type == tabs[selectedTabIndex] }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "🚗 গাড়ি ভাড়া সার্ভিস",
            style = MaterialTheme.typography.titleLarge
        )

        ScrollableTabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- DEBUGGING TEXT ---
        Text(
            text = "Total cars from DB: ${carList.size} | Filtered cars shown: ${filteredList.size}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Red
        )
        // --- END DEBUGGING TEXT ---

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredList.isEmpty()) {
            Text("কোনো তথ্য পাওয়া যায়নি", color = Color.Gray)
        } else {
            LazyColumn {
                items(filteredList) { carInfo ->
                    CarRentCard(info = carInfo)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { showAddDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
            Spacer(modifier = Modifier.width(8.dp))
            Text("নতুন গাড়ির তথ্য যোগ করুন")
        }
    }

    if (showAddDialog) {
        AddCarDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newCar ->
                viewModel.addCar(newCar)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun CarRentCard(info: CarInfo) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🚘 গাড়ির নাম: ${info.carName}", fontSize = 16.sp)
            Text("👨‍✈️ ড্রাইভারের নাম: ${info.driverName}", fontSize = 14.sp)
            Text("📋 ধরন: ${info.type}", fontSize = 14.sp)
            Text("🏘️ থানা: ${info.thana}", fontSize = 14.sp)
            Text(
                text = "📞 কল করুন: ${info.phone}",
                color = Color(0xFF1976D2),
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info.phone}"))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarDialog(onDismiss: () -> Unit, onAdd: (CarInfo) -> Unit) {
    var carName by remember { mutableStateOf("") }
    var driverName by remember { mutableStateOf("") }
    var thana by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    val carTypes = listOf("অ্যাম্বুলেন্স", "প্রাইভেট কার", "মাইক্রোবাস", "পিক আপ", "ট্রাক")
    var selectedType by remember { mutableStateOf(carTypes[0]) }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("নতুন গাড়ির তথ্য যোগ করুন") },
        text = {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                OutlinedTextField(
                    value = carName,
                    onValueChange = { carName = it },
                    label = { Text("গাড়ির নাম") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = driverName,
                    onValueChange = { driverName = it },
                    label = { Text("ড্রাইভারের নাম") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded }
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        label = { Text("গাড়ির ধরন") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        carTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = thana,
                    onValueChange = { thana = it },
                    label = { Text("থানা") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("মোবাইল নম্বর") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    Log.d("CarRentScreen", "Add button clicked")
                    if (carName.isNotBlank() && driverName.isNotBlank()) {
                        onAdd(CarInfo(null, carName, driverName, selectedType, thana, phone))
                    }
                }
            ) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}