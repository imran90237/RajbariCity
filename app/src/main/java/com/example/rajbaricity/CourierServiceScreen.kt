package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Data Model
data class CourierInfo(
    val name: String,
    val address: String,
    val mapUrl: String,
    val phone: String
)

// Main Screen
@Composable
fun CourierServiceScreen(onHomeClick: () -> Unit = {}) {
    var courierList by remember {
        mutableStateOf(
            listOf(
                CourierInfo(
                    name = "Sundarban Courier Service (সুন্দরবন কুরিয়ার সার্ভিস)",
                    address = "ব্র্যাক ব্যাংকের নিচে, মেইন রোড, পাবলিক হেলথ, মনিক ম্যানশন, সজ্জনখান্দা, রাজবাড়ী সদর",
                    mapUrl = "",
                    phone = "01963603071, 01726233505, 01324727909"
                ),
                CourierInfo(
                    name = "Pathao Courier (পাঠাও কুরিয়ার)",
                    address = "QJ3W+WW5, সজ্জনখান্দা রোড, রাজবাড়ী",
                    mapUrl = "",
                    phone = "01958-522338"
                ),
                CourierInfo(
                    name = "SA Paribahan (এস এ পরিবহন)",
                    address = "রাজবাড়ী ইংলিশ মার্কেট, QJ6W+JP7, রাজবাড়ী",
                    mapUrl = "",
                    phone = "01766-688384"
                ),
                CourierInfo(
                    name = "RedX (রেডএক্স) - রাজবাড়ী হাব",
                    address = "PJXR+H7C, রাজবাড়ী",
                    mapUrl = "",
                    phone = "01324-711562"
                ),
                CourierInfo(
                    name = "Steadfast Courier (স্টেডফাস্ট কুরিয়ার) - পাংশা",
                    address = "QCJ7+244, বিষ্ণুপুর, পাংশা, রাজবাড়ী",
                    mapUrl = "",
                    phone = "01824239406"
                )
            )
        )
    }

    var showForm by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "📦 কুরিয়ার সার্ভিস",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (showForm) {
                AddCourierForm(
                    onCourierAdded = { newCourier ->
                        courierList = courierList + newCourier
                        showForm = false
                    },
                    onCancel = {
                        showForm = false
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                courierList.forEach { courier ->
                    CourierCard(info = courier)
                }
            }
        }

        // Floating Action Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { showForm = !showForm },
                shape = CircleShape,
                containerColor = Color(0xFF1976D2)
            ) {
                Icon(
                    imageVector = if (showForm) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = "Toggle Form",
                    tint = Color.White
                )
            }
        }
    }
}

// Courier Card
@Composable
fun CourierCard(info: CourierInfo) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🏢 নাম: ${info.name}", fontSize = 16.sp)
            Text("📍 ঠিকানা: ${info.address}", fontSize = 14.sp)

            if (info.mapUrl.isNotBlank()) {
                Text(
                    text = "🗺️ গুগল ম্যাপে দেখুন",
                    color = Color(0xFF1976D2),
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(info.mapUrl))
                            context.startActivity(intent)
                        }
                        .padding(vertical = 4.dp)
                )
            }

            Text(
                text = "📞 মোবাইল: ${info.phone}",
                color = Color(0xFF0D47A1),
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${info.phone}"))
                        context.startActivity(intent)
                    }
            )
        }
    }
}

// Add Courier Form
@Composable
fun AddCourierForm(
    onCourierAdded: (CourierInfo) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var mapUrl by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("📝 নতুন কুরিয়ার এন্ট্রি", fontSize = 18.sp)

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("🏢 নাম") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("📍 ঠিকানা") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = mapUrl,
            onValueChange = { mapUrl = it },
            label = { Text("🗺️ গুগল ম্যাপ লিংক") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("📞 মোবাইল") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onCourierAdded(CourierInfo(name, address, mapUrl, phone))
                        name = ""
                        address = ""
                        mapUrl = ""
                        phone = ""
                    }
                }
            ) {
                Text("✅ Save")
            }

            Button(
                onClick = {
                    name = ""
                    address = ""
                    mapUrl = ""
                    phone = ""
                    onCancel()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("❌ Cancel")
            }
        }
    }
}
