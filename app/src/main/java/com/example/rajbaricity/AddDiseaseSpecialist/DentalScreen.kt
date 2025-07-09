package com.example.rajbaricity.AddDiseaseSpecialist

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R

data class DentalDoctor(
    val photoResId: Int = R.drawable.default_doctor,
    val photoUri: Uri? = null,
    val name: String,
    val specialty: String,
    val qualification: String,
    val workplace: String,
    val diseasesTreated: String,
    val chamber1: String,
    val chamber2: String,
    // val chamber3: String,
    val mapLink: String
)

@Composable
fun DentalScreen() {
    val context = LocalContext.current
    var showForm by remember { mutableStateOf(false) }
    var doctors by remember {
        mutableStateOf(
            mutableListOf(
                DentalDoctor(
                    photoResId = R.drawable.default_doctor,
                    name = "ডাঃ ফারহানা ইসলাম",
                    specialty = "ডেন্টাল সার্জন",
                    qualification = "BDS, MDS (Oral Surgery)",
                    workplace = "ঢাকা ডেন্টাল হাসপাতাল",
                    diseasesTreated = "দাঁতের ব্যথা, দাঁতের সার্জারি, মাড়ির সমস্যা",
                    chamber1 = "ডেন্টাল ক্লিনিক, গুলশান ১",
                    chamber2 = "সিটি হেলথ ডেন্টাল সেন্টার",
                    // chamber3 = "রূপগঞ্জ ক্লিনিক",
                    mapLink = "https://maps.app.goo.gl/dental-location"
                )
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🦷 দন্ত বিশেষজ্ঞ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(doctors) { doctor ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = doctor.photoResId),
                                contentDescription = "Doctor Photo",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 16.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("বিশেষজ্ঞ: ${doctor.specialty}", fontSize = 14.sp)
                                Text("যোগ্যতা: ${doctor.qualification}", fontSize = 14.sp)
                                Text("কর্মস্থল: ${doctor.workplace}", fontSize = 14.sp)
                                Text("চিকিৎসিত রোগসমূহ: ${doctor.diseasesTreated}", fontSize = 14.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("চেম্বার সমূহ:", fontWeight = FontWeight.Bold)
                        Text("১. ${doctor.chamber1}")
                        Text("২. ${doctor.chamber2}")
                        // Text("৩. ${doctor.chamber3}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(doctor.mapLink))
                                context.startActivity(intent)
                            }) {
                                Text("📍 লোকেশন")
                            }

                            Button(onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:010000000"))
                                context.startActivity(intent)
                            }) {
                                Text("📞 সিরিয়াল")
                            }
                        }
                    }
                }
            }
        }

        if (!showForm) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = { showForm = true },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Doctor",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        if (showForm) {
            AddDentalDoctorForm(
                onDoctorAdded = {
                    doctors.add(it)
                    showForm = false
                },
                onCancel = {
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun AddDentalDoctorForm(onDoctorAdded: (DentalDoctor) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var diseasesTreated by remember { mutableStateOf("") }
    var chamber1 by remember { mutableStateOf("") }
    var chamber2 by remember { mutableStateOf("") }
    // var chamber3 by remember { mutableStateOf("") }
    var mapLink by remember { mutableStateOf("") }

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        photoUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("📷 ছবি নির্বাচন করুন")
        }

        photoUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Selected Doctor Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
            )
        }

        OutlinedTextField(name, { name = it }, label = { Text("ডাক্তারের নাম") })
        OutlinedTextField(specialty, { specialty = it }, label = { Text("বিশেষজ্ঞ বিভাগ") })
        OutlinedTextField(qualification, { qualification = it }, label = { Text("যোগ্যতা") })
        OutlinedTextField(workplace, { workplace = it }, label = { Text("কর্মস্থল") })
        OutlinedTextField(diseasesTreated, { diseasesTreated = it }, label = { Text("চিকিৎসিত রোগসমূহ") })
        OutlinedTextField(chamber1, { chamber1 = it }, label = { Text("চেম্বার ১") })
        OutlinedTextField(chamber2, { chamber2 = it }, label = { Text("চেম্বার ২") })
        // OutlinedTextField(chamber3, { chamber3 = it }, label = { Text("চেম্বার ৩") })
        OutlinedTextField(mapLink, { mapLink = it }, label = { Text("Google Map লিংক") })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onDoctorAdded(
                        DentalDoctor(
                            photoResId = R.drawable.default_doctor,
                            name = name,
                            specialty = specialty,
                            qualification = qualification,
                            workplace = workplace,
                            diseasesTreated = diseasesTreated,
                            chamber1 = chamber1,
                            chamber2 = chamber2,
                            // chamber3 = chamber3,
                            mapLink = mapLink
                        )
                    )
                    // Clear form fields
                    name = ""
                    specialty = ""
                    qualification = ""
                    workplace = ""
                    diseasesTreated = ""
                    chamber1 = ""
                    chamber2 = ""
                    // chamber3 = ""
                    mapLink = ""
                    photoUri = null
                }
            }) {
                Text("✅ Save Doctor Info")
            }

            OutlinedButton(
                onClick = {
                    // Clear and cancel
                    name = ""
                    specialty = ""
                    qualification = ""
                    workplace = ""
                    diseasesTreated = ""
                    chamber1 = ""
                    chamber2 = ""
                    // chamber3 = ""
                    mapLink = ""
                    photoUri = null
                    onCancel()
                },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("❌ Cancel")
            }
        }
    }
}
