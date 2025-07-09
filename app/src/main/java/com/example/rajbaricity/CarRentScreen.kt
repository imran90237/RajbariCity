package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

data class CarInfo(
    val carName: String,
    val driverName: String,
    val type: String,
    val thana: String,
    val phone: String
)

@Composable
fun CarRentScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("অ্যাম্বুলেন্স", "প্রাইভেট কার", "মাইক্রোবাস", "পিক আপ", "ট্রাক")

    var carList by remember {
        mutableStateOf(
            listOf(
                CarInfo("Toyota Hiace", "মোঃ কামাল", "মাইক্রোবাস", "রাজবাড়ী সদর", "017XXXXXXXX"),
                CarInfo("Nissan Ambulance", "আব্দুল মালেক", "অ্যাম্বুলেন্স", "পাংশা", "018XXXXXXXX")
            )
        )
    }

    var showAddDialog by remember { mutableStateOf(false) }

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

        Spacer(modifier = Modifier.height(16.dp))

        val filteredList = carList.filter { it.type == tabs[selectedTabIndex] }

        if (filteredList.isEmpty()) {
            Text("কোনো তথ্য পাওয়া যায়নি", color = Color.Gray)
        } else {
            filteredList.forEach {
                CarRentCard(info = it)
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
                carList = carList + newCar
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

@Composable
fun AddCarDialog(onDismiss: () -> Unit, onAdd: (CarInfo) -> Unit) {
    var carName by remember { mutableStateOf("") }
    var driverName by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var thana by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("নতুন গাড়ির তথ্য যোগ করুন") },
        text = {
            Column {
                OutlinedTextField(value = carName, onValueChange = { carName = it }, label = { Text("গাড়ির নাম") })
                OutlinedTextField(value = driverName, onValueChange = { driverName = it }, label = { Text("ড্রাইভারের নাম") })
                OutlinedTextField(value = type, onValueChange = { type = it }, label = { Text("গাড়ির ধরন") })
                OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("মোবাইল নম্বর") })
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (carName.isNotBlank() && driverName.isNotBlank() && type.isNotBlank()) {
                        onAdd(CarInfo(carName, driverName, type, thana, phone))
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
