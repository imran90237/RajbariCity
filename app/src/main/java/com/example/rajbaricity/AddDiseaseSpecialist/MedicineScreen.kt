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

data class MedicineDoctor(
    val photoResId: Int = R.drawable.default_doctor,
    val photoUri: Uri? = null,
    val name: String,
    val specialty: String,
    val qualification: String,
    val workplace: String,
    val diseasesTreated: String,
    val chamber1: String,
    val chamber2: String,
    // val chamber3: String,  // Commented out as requested
    val mapLink: String
)

@Composable
fun MedicineScreen() {
    val context = LocalContext.current
    var showForm by remember { mutableStateOf(false) }
    var doctors by remember {
        mutableStateOf(
            mutableListOf(
                MedicineDoctor(
                    name = "ডাঃ আজহারুল ইসলাম",
                    specialty = "মেডিসিন বিশেষজ্ঞ",
                    qualification = "MBBS, MD (Medicine)",
                    workplace = "রাজবাড়ী মেডিকেল কলেজ হাসপাতাল",
                    diseasesTreated = "ডায়াবেটিস, উচ্চ রক্তচাপ, ব্রঙ্কাইটিস, ফ্লু",
                    chamber1 = "গ্রামীণ ক্লিনিক, রাজবাড়ী",
                    chamber2 = "সিটি ক্লিনিক, পাংশা",
                    // chamber3 = "রয়েল হাসপাতাল, গোয়ালন্দ",  // Commented out
                    mapLink = "https://maps.app.goo.gl/medicinedoctor-location"
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
            text = "🩺 ওষুধ বিশেষজ্ঞ",
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
                            if (doctor.photoUri != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(doctor.photoUri),
                                    contentDescription = "Doctor Photo",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(end = 16.dp)
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = doctor.photoResId),
                                    contentDescription = "Doctor Photo",
                                    modifier = Modifier
                                        .size(80.dp)
                                        .padding(end = 16.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("বিশেষজ্ঞ: ${doctor.specialty}", fontSize = 14.sp)
                                Text("যোগ্যতা: ${doctor.qualification}", fontSize = 14.sp)
                                Text("কর্মস্থল: ${doctor.workplace}", fontSize = 14.sp)
                                Text("চিকিৎসা: ${doctor.diseasesTreated}", fontSize = 14.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("চেম্বার সমূহ:", fontWeight = FontWeight.Bold)
                        Text("১. ${doctor.chamber1}")
                        Text("২. ${doctor.chamber2}")
                        // Text("৩. ${doctor.chamber3}")  // Commented out

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
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:01700000000"))
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
            AddMedicineDoctorForm(
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
fun AddMedicineDoctorForm(onDoctorAdded: (MedicineDoctor) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var diseasesTreated by remember { mutableStateOf("") }
    var chamber1 by remember { mutableStateOf("") }
    var chamber2 by remember { mutableStateOf("") }
    // var chamber3 by remember { mutableStateOf("") } // Commented out as requested
    var mapLink by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> photoUri = uri }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("📷 ছবি নির্বাচন করুন")
        }

        photoUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Selected Doctor Image",
                modifier = Modifier.size(100.dp).padding(4.dp)
            )
        }

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("ডাক্তারের নাম") })
        OutlinedTextField(value = specialty, onValueChange = { specialty = it }, label = { Text("বিশেষজ্ঞ বিভাগ") })
        OutlinedTextField(value = qualification, onValueChange = { qualification = it }, label = { Text("যোগ্যতা") })
        OutlinedTextField(value = workplace, onValueChange = { workplace = it }, label = { Text("কর্মস্থল") })
        OutlinedTextField(value = diseasesTreated, onValueChange = { diseasesTreated = it }, label = { Text("চিকিৎসিত রোগসমূহ") })
        OutlinedTextField(value = chamber1, onValueChange = { chamber1 = it }, label = { Text("চেম্বার ১") })
        OutlinedTextField(value = chamber2, onValueChange = { chamber2 = it }, label = { Text("চেম্বার ২") })
        // OutlinedTextField(value = chamber3, onValueChange = { chamber3 = it }, label = { Text("চেম্বার ৩") }) // commented out
        OutlinedTextField(value = mapLink, onValueChange = { mapLink = it }, label = { Text("Google Map লিংক") })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onDoctorAdded(
                        MedicineDoctor(
                            name = name,
                            specialty = specialty,
                            qualification = qualification,
                            workplace = workplace,
                            diseasesTreated = diseasesTreated,
                            chamber1 = chamber1,
                            chamber2 = chamber2,
                            // chamber3 = chamber3,  // commented out
                            mapLink = mapLink,
                            photoUri = photoUri
                        )
                    )
                    // Reset fields after saving
                    name = ""
                    specialty = ""
                    qualification = ""
                    workplace = ""
                    diseasesTreated = ""
                    chamber1 = ""
                    chamber2 = ""
                    // chamber3 = ""  // commented out
                    mapLink = ""
                    photoUri = null
                }
            }) {
                Text("✅ Save Doctor Info")
            }

            OutlinedButton(onClick = {
                // Reset all fields and cancel form
                name = ""
                specialty = ""
                qualification = ""
                workplace = ""
                diseasesTreated = ""
                chamber1 = ""
                chamber2 = ""
                // chamber3 = ""  // commented out
                mapLink = ""
                photoUri = null
                onCancel()
            }) {
                Text("❌ Cancel")
            }
        }
    }
}
