package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.model.Hospital
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun HospitalScreen(navController: NavController, viewModel: RajbariViewModel = viewModel()) {
    val tabs = listOf("সরকারি হাসপাতাল", "ক্লিনিক", "বেসরকারি হাসপাতাল", "ডায়াগনস্টিক সেন্টার")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val hospitalListFromDb by viewModel.hospitals.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    // Static Data
    val staticGovtHospitals = listOf(
        Hospital(id = -1L, name = "রাজবাড়ী সদর হাসপাতাল", address = "রাজবাড়ী শহর", phone = "01712345678", hours = "সকাল ৮টা - রাত ৮টা", hasEmergency = true, mapUrl = "https://maps.app.goo.gl/UcFyYjE6Hv9Z1u3v6", photoUrl = null, photoResId = R.drawable.sadorhospital_photo, type = "সরকারি হাসপাতাল"),
        Hospital(id = -2L, name = "গোয়ালন্দ উপজেলা স্বাস্থ্য কমপ্লেক্স", address = "গোয়ালন্দ", phone = "01798765432", hours = "সকাল ৯টা - বিকাল ৫টা", hasEmergency = false, mapUrl = "https://maps.app.goo.gl/sample10", photoUrl = null, photoResId = R.drawable.gualondo_photo, type = "সরকারি হাসপাতাল")
    )
    val staticClinics = listOf(
        Hospital(id = -3L, name = "রাজবাড়ী ক্লিনিক", address = "রাজবাড়ী", phone = "01711185282", hours = "সকাল ৯টা - বিকাল ৫টা", hasEmergency = false, mapUrl = "https://maps.app.goo.gl/sample4", photoUrl = null, photoResId = R.drawable.rajbariclinic_photo, type = "ক্লিনিক")
    )
    val staticPrivateHospitals = listOf(
        Hospital(id = -4L, name = "সেন্ট্রাল হাসপাতাল রাজবাড়ী", address = "বড়পুল, রাজবাড়ী", phone = "01700011111", hours = "সকাল ৯টা - রাত ৯টা", hasEmergency = true, mapUrl = "https://maps.app.goo.gl/sample1", photoUrl = null, photoResId = R.drawable.centralhospatal_photo, type = "বেসরকারি হাসপাতাল")
    )
    val staticDiagnosticCenters = listOf(
        Hospital(id = -5L, name = "রাজবাড়ী ডায়াগনস্টিক সেন্টার demo", address = "স্টেশন রোড", phone = "01700077777", hours = "সকাল ৮টা - রাত ৮টা", hasEmergency = false, mapUrl = "https://maps.app.goo.gl/sample7", photoUrl = null, photoResId = R.drawable.diagnostic_photo, type = "ডায়াগনস্টিক সেন্টার")
    )

    val combinedList by remember(hospitalListFromDb, selectedTabIndex) {
        derivedStateOf {
            val dbList = hospitalListFromDb.filter { it.type == tabs[selectedTabIndex] }
            val staticList = when (selectedTabIndex) {
                0 -> staticGovtHospitals
                1 -> staticClinics
                2 -> staticPrivateHospitals
                3 -> staticDiagnosticCenters
                else -> emptyList()
            }
            // Combine and remove duplicates (preferring DB data over static)
            (dbList + staticList).distinctBy { it.name }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "🏥 হাসপাতাল সম্পর্কিত তথ্য",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 0.dp) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (combinedList.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("কোনো তথ্য পাওয়া যায়নি", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(combinedList, key = { it.id ?: it.hashCode() }) { hospital ->
                    HospitalCard(hospital = hospital)
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
            Text("নতুন হাসপাতালের তথ্য যোগ করুন")
        }
    }

    if (showAddDialog) {
        AddHospitalDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { newHospital ->
                viewModel.addHospital(newHospital)
                showAddDialog = false
            },
            selectedType = tabs[selectedTabIndex]
        )
    }
}

@Composable
fun HospitalCard(hospital: Hospital) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F6FB)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = hospital.photoResId ?: hospital.photoUrl,
                contentDescription = hospital.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.default_hospital)
            )
            Text(hospital.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text("ঠিকানা: ${hospital.address}", fontSize = 14.sp)
            Text("সময়: ${hospital.hours}", fontSize = 14.sp)
            Text(
                text = "ফোন: ${hospital.phone}",
                color = Color(0xFF1976D2),
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${hospital.phone}"))
                    context.startActivity(intent)
                }
            )
            Text(
                "অবস্থান: ${hospital.mapUrl}",
                color = Color(0xFF1976D2),
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(hospital.mapUrl))
                    context.startActivity(intent)
                }
            )
            Text(
                text = if (hospital.hasEmergency) "ইমারজেন্সি সেবা রয়েছে" else "ইমারজেন্সি সেবা নেই",
                color = if (hospital.hasEmergency) Color(0xFF1B5E20) else Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHospitalDialog(onDismiss: () -> Unit, onAdd: (Hospital) -> Unit, selectedType: String) {
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf("") }
    var mapUrl by remember { mutableStateOf("") }
    var hasEmergency by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("নতুন হাসপাতালের তথ্য যোগ করুন") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = hours, onValueChange = { hours = it }, label = { Text("সময়") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = mapUrl, onValueChange = { mapUrl = it }, label = { Text("ম্যাপ URL") }, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasEmergency, onCheckedChange = { hasEmergency = it })
                    Text("ইমারজেন্সি সেবা রয়েছে")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { launcher.launch("image/*") }) {
                    Text("ছবি নির্বাচন করুন")
                }
                imageUri?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = it),
                        contentDescription = "Selected Image",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && address.isNotBlank()) {
                        onAdd(
                            Hospital(
                                id = null,
                                name = name,
                                address = address,
                                phone = phone,
                                hours = hours,
                                hasEmergency = hasEmergency,
                                mapUrl = mapUrl,
                                photoUrl = imageUri?.toString(),
                                photoResId = null,
                                type = selectedType
                            )
                        )
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