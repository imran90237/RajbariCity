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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rajbaricity.model.Courier
import com.example.rajbaricity.ui.RajbariViewModel

// Main Screen
@Composable
fun CourierServiceScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getCouriers()
    }

    var showForm by remember { mutableStateOf(false) }
    val couriers by viewModel.couriers.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val staticCouriers = remember {
        listOf(
            Courier(
                name = "Sundarban Courier Service ( सुंदरवन कुरियर सर्विस)",
                address = "ব্র্যাক ব্যাংকের নিচে, মেইন রোড, পাবলিক হেলথ, মনিক ম্যানশন, সজ্জনখান্দা, রাজবাড়ী সদর",
                mapUrl = "",
                phone = "01963603071, 01726233505, 01324727909"
            ),
            Courier(
                name = "Pathao Courier (পাঠাও কুরিয়ার)",
                address = "QJ3W+WW5, সজ্জনখান্দা রোড, রাজবাড়ী",
                mapUrl = "",
                phone = "01958-522338"
            ),
            Courier(
                name = "SA Paribahan (এস এ পরিবহন)",
                address = "রাজবাড়ী ইংলিশ মার্কেট, QJ6W+JP7, রাজবাড়ী",
                mapUrl = "",
                phone = "01766-688384"
            ),
            Courier(
                name = "RedX (রেডএক্স) - রাজবাড়ী হাব",
                address = "PJXR+H7C, রাজবাড়ী",
                mapUrl = "",
                phone = "01324-711562"
            ),
            Courier(
                name = "Steadfast Courier (স্টেডফাস্ট কুরিয়ার) - পাংশা",
                address = "QCJ7+244, বিষ্ণুপুর, পাংশা, রাজবাড়ী",
                mapUrl = "",
                phone = "01824239406"
            )
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Courier",
                    tint = Color.Black
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Text(
                text = "📦 কুরিয়ার সার্ভিস",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("কুরিয়ারের নাম দিয়ে খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = couriers.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
                items(filteredList) { courier ->
                    CourierCard(courier)
                }
                items(staticCouriers) { courier ->
                    CourierCard(courier)
                }
            }
        }

        if (showForm) {
            AddCourierForm(
                onCourierAdded = { newCourier ->
                    viewModel.addCourier(newCourier)
                    showForm = false
                },
                onCancel = {
                    showForm = false
                }
            )
        }
    }
}

// Courier Card
@Composable
fun CourierCard(courier: Courier) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("🏢 নাম: ${courier.name}", fontSize = 16.sp)
            Text("📍 ঠিকানা: ${courier.address}", fontSize = 14.sp)

            if (courier.mapUrl.isNotBlank()) {
                Text(
                    text = "🗺️ গুগল ম্যাপে দেখুন",
                    color = Color(0xFF1976D2),
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(courier.mapUrl))
                            context.startActivity(intent)
                        }
                        .padding(vertical = 4.dp)
                )
            }

            Text(
                text = "📞 মোবাইল: ${courier.phone}",
                color = Color(0xFF0D47A1),
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${courier.phone}"))
                        context.startActivity(intent)
                    }
            )
        }
    }
}

// Add Courier Form
@Composable
fun AddCourierForm(
    onCourierAdded: (Courier) -> Unit,
    onCancel: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var mapUrl by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("নতুন কুরিয়ার এন্ট্রি") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onCourierAdded(Courier(name, address, mapUrl, phone))
                    }
                }
            ) {
                Text("✅ Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("❌ Cancel")
            }
        }
    )
}
