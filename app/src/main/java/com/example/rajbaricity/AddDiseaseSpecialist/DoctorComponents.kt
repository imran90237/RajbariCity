package com.example.rajbaricity.AddDiseaseSpecialist

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R
import com.example.rajbaricity.model.Doctor

@Composable
fun DoctorCard(doctor: Doctor) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = doctor.photoUrl.ifBlank { R.drawable.logo },
                contentDescription = doctor.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )
            Column {
                Text(text = doctor.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "বিশেষজ্ঞ: ${doctor.specialty}")
                Text(text = "যোগ্যতা: ${doctor.qualification}")
                Text(text = "কর্মস্থল: ${doctor.workplace}")
                Text(text = "রোগ: ${doctor.diseasesTreated}")
                Text(text = "চেম্বার: ${doctor.chamber1}")
            }
        }
    }
}

@Composable
fun AddDoctorForm(
    specialty: String,
    onDoctorAdded: (Doctor) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var diseasesTreated by remember { mutableStateOf("") }
    var chamber1 by remember { mutableStateOf("") }
    var mapLink by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("নতুন ডাক্তারের তথ্য যোগ করুন") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(model = selectedImageUri),
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("ছবি নির্বাচন করুন", textAlign = TextAlign.Center)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") })
                OutlinedTextField(value = qualification, onValueChange = { qualification = it }, label = { Text("যোগ্যতা") })
                OutlinedTextField(value = workplace, onValueChange = { workplace = it }, label = { Text("কর্মস্থল") })
                OutlinedTextField(value = diseasesTreated, onValueChange = { diseasesTreated = it }, label = { Text("চিকিৎসিত রোগসমূহ") })
                OutlinedTextField(value = chamber1, onValueChange = { chamber1 = it }, label = { Text("চেম্বার") })
                OutlinedTextField(value = mapLink, onValueChange = { mapLink = it }, label = { Text("Google Map লিংক") })
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onDoctorAdded(
                        Doctor(
                            name = name,
                            specialty = specialty,
                            qualification = qualification,
                            workplace = workplace,
                            diseasesTreated = diseasesTreated,
                            chamber1 = chamber1,
                            mapLink = mapLink,
                            photoUrl = selectedImageUri?.toString() ?: ""
                        )
                    )
                }
            }) {
                Text("সংরক্ষণ করুন")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onCancel) {
                Text("বাতিল করুন")
            }
        }
    )
}
