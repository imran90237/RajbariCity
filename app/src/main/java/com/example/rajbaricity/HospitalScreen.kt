package com.example.rajbaricity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

data class Hospital(
    val name: String,
    val address: String,
    val phone: String,
    val hours: String,
    val hasEmergency: Boolean,
    val mapUrl: String,
    val photoResId: Int = R.drawable.default_hospital,
    val photoUri: Uri? = null
)

@Composable
fun HospitalScreen(navController: NavController) {
    val tabs = listOf("সরকারি হাসপাতাল", "ক্লিনিক", "বেসরকারি হাসপাতাল", "ডায়াগনস্টিক সেন্টার")
    var selectedTabIndex by remember { mutableStateOf(0) }

    var govtHospitals by remember {
        mutableStateOf(
            listOf(
                Hospital("রাজবাড়ী সদর হাসপাতাল", "রাজবাড়ী শহর", "01712345678", "সকাল ৮টা - রাত ৮টা", true, "https://maps.app.goo.gl/UcFyYjE6Hv9Z1u3v6", R.drawable.sadorhospital_photo),
                Hospital("গোয়ালন্দ উপজেলা স্বাস্থ্য কমপ্লেক্স", "গোয়ালন্দ", "01798765432", "সকাল ৯টা - বিকাল ৫টা", false, "https://maps.app.goo.gl/sample10", R.drawable.gualondo_photo)
            )
        )
    }

    var clinics by remember {
        mutableStateOf(listOf(
            Hospital("রাজবাড়ী ক্লিনিক", "রাজবাড়ী", "01711185282", "সকাল ৯টা - বিকাল ৫টা", false, "https://maps.app.goo.gl/sample4", R.drawable.rajbariclinic_photo)
        ))
    }

    var privateHospitals by remember {
        mutableStateOf(listOf(
            Hospital("সেন্ট্রাল হাসপাতাল রাজবাড়ী", "বড়পুল, রাজবাড়ী", "01700011111", "সকাল ৯টা - রাত ৯টা", true, "https://maps.app.goo.gl/sample1", R.drawable.centralhospatal_photo)
        ))
    }

    var diagnosticCenters by remember {
        mutableStateOf(listOf(
            Hospital("রাজবাড়ী ডায়াগনস্টিক সেন্টার demo", "স্টেশন রোড", "01700077777", "সকাল ৮টা - রাত ৮টা", false, "https://maps.app.goo.gl/sample7", R.drawable.default_hospital)
        ))
    }

    var showForm by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("🏥 হাসপাতাল সম্পর্কিত তথ্য", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))

        ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 0.dp) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTabIndex == index, onClick = { selectedTabIndex = index }) {
                    Text(text = title, fontSize = 16.sp, modifier = Modifier.padding(12.dp))
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        when (selectedTabIndex) {
            0 -> HospitalList(govtHospitals)
            1 -> HospitalList(clinics)
            2 -> HospitalList(privateHospitals)
            3 -> HospitalList(diagnosticCenters)
        }

        Box(modifier = Modifier.fillMaxWidth().padding(end = 8.dp)) {
            FloatingActionButton(
                onClick = { showForm = true },
                modifier = Modifier.size(56.dp).align(Alignment.TopEnd)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "নতুন তথ্য যোগ করুন", tint = Color.Black)
            }
        }

        if (showForm) {
            AddHospitalDialog(
                onDismiss = { showForm = false },
                onSave = { newHospital ->
                    when (selectedTabIndex) {
                        0 -> govtHospitals += newHospital
                        1 -> clinics += newHospital
                        2 -> privateHospitals += newHospital
                        3 -> diagnosticCenters += newHospital
                    }
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun HospitalList(hospitals: List<Hospital>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        items(hospitals) { hospital -> HospitalCard(hospital) }
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
        Row(modifier = Modifier.padding(12.dp)) {
            if (hospital.photoUri != null) {
                AsyncImage(
                    model = hospital.photoUri,
                    contentDescription = hospital.name,
                    modifier = Modifier.size(100.dp).padding(end = 12.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = hospital.photoResId),
                    contentDescription = hospital.name,
                    modifier = Modifier.size(100.dp).padding(end = 12.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(hospital.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("📍 ঠিকানা: ${hospital.address}", fontSize = 14.sp)
                Text("📞 ফোন: ${hospital.phone}", fontSize = 14.sp)
                Text("🕒 সময়: ${hospital.hours}", fontSize = 14.sp)
                Text(
                    "🚑 ${if (hospital.hasEmergency) "ইমারজেন্সি সেবা রয়েছে" else "ইমারজেন্সি সেবা নেই"}",
                    fontSize = 14.sp,
                    color = if (hospital.hasEmergency) Color(0xFF1B5E20) else Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextButton(onClick = {
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(hospital.mapUrl)))
                }) {
                    Text("🗺️ ম্যাপে দেখুন", color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Composable
fun AddHospitalDialog(onDismiss: () -> Unit, onSave: (Hospital) -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var hours by remember { mutableStateOf(TextFieldValue("")) }
    var mapUrl by remember { mutableStateOf(TextFieldValue("")) }
    var hasEmergency by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("নতুন হাসপাতালের তথ্য যোগ করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(name, { name = it }, label = { Text("নাম") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(address, { address = it }, label = { Text("ঠিকানা") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(phone, { phone = it }, label = { Text("ফোন") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(hours, { hours = it }, label = { Text("সময়") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(mapUrl, { mapUrl = it }, label = { Text("ম্যাপে URL") }, modifier = Modifier.fillMaxWidth())

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = hasEmergency, onCheckedChange = { hasEmergency = it })
                    Spacer(Modifier.width(8.dp))
                    Text("ইমারজেন্সি সেবা রয়েছে")
                }

                Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                    Text("📷 ছবি নির্বাচন করুন")
                }

                selectedImageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Selected Hospital Photo",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.text.isNotBlank() && address.text.isNotBlank() && phone.text.isNotBlank() && hours.text.isNotBlank()) {
                    onSave(
                        Hospital(
                            name = name.text.trim(),
                            address = address.text.trim(),
                            phone = phone.text.trim(),
                            hours = hours.text.trim(),
                            hasEmergency = hasEmergency,
                            mapUrl = mapUrl.text.trim().ifBlank { "https://maps.google.com" },
                            photoUri = selectedImageUri
                        )
                    )
                }
            }) {
                Text("সংরক্ষণ")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("বাতিল") }
        }
    )
}
