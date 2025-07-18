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

data class ENTDoctor(
    val photoResId: Int = R.drawable.default_doctor,
    val photoUri: Uri? = null,
    val name: String,
    val specialty: String,
    val qualification: String,
    val workplace: String,
    val diseasesTreated: String,
    val chamber1: String,
    val mapLink: String
)

@Composable
fun ENTScreen() {
    val context = LocalContext.current
    var showForm by remember { mutableStateOf(false) }
    var doctors by remember {
        mutableStateOf(
            mutableListOf(
                ENTDoctor(
                    photoResId = R.drawable.default_doctor,
                    name = "ডাঃ মাসুদুর রহমান",
                    specialty = "ইএনটি বিশেষজ্ঞ",
                    qualification = "MBBS, DLO (ENT), FCPS",
                    workplace = "রাজবাড়ী সদর হাসপাতাল",
                    diseasesTreated = "কান, নাক, গলা সমস্যা, টনসিল, হেয়ার লস, সাইনাস",
                    chamber1 = "রাজবাড়ী ক্লিনিক, ২য় তলা, রুম ৭",
                    mapLink = "https://maps.app.goo.gl/rajbari-ent-clinic"
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
            text = "👂 কান, নাক, গলা বিশেষজ্ঞ",
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
                                Text("চিকিৎসিত রোগ: ${doctor.diseasesTreated}", fontSize = 14.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("চেম্বার:", fontWeight = FontWeight.Bold)
                        Text(doctor.chamber1)

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
            AddENTDoctorForm(
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
fun AddENTDoctorForm(onDoctorAdded: (ENTDoctor) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var diseasesTreated by remember { mutableStateOf("") }
    var chamber1 by remember { mutableStateOf("") }
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
        OutlinedTextField(diseasesTreated, { diseasesTreated = it }, label = { Text("চিকিৎসিত রোগ") })
        OutlinedTextField(chamber1, { chamber1 = it }, label = { Text("চেম্বার") })
        OutlinedTextField(mapLink, { mapLink = it }, label = { Text("Google Map লিংক") })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onDoctorAdded(
                        ENTDoctor(
                            photoResId = R.drawable.default_doctor,
                            name = name,
                            specialty = specialty,
                            qualification = qualification,
                            workplace = workplace,
                            diseasesTreated = diseasesTreated,
                            chamber1 = chamber1,
                            mapLink = mapLink
                        )
                    )
                    name = ""
                    specialty = ""
                    qualification = ""
                    workplace = ""
                    diseasesTreated = ""
                    chamber1 = ""
                    mapLink = ""
                    photoUri = null
                }
            }) {
                Text("✅ Save Doctor Info")
            }

            OutlinedButton(
                onClick = {
                    name = ""
                    specialty = ""
                    qualification = ""
                    workplace = ""
                    diseasesTreated = ""
                    chamber1 = ""
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
