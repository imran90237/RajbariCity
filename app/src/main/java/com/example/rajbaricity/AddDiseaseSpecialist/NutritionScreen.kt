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

data class Nutritionist(
    val photoResId: Int = R.drawable.default_doctor,
    val photoUri: Uri? = null,
    val name: String,
    val specialty: String,
    val qualification: String,
    val workplace: String,
    val servicesProvided: String,
    val chamber1: String,
    val mapLink: String
)

@Composable
fun NutritionScreen() {
    val context = LocalContext.current
    var showForm by remember { mutableStateOf(false) }
    var nutritionists by remember {
        mutableStateOf(
            mutableListOf(
                Nutritionist(
                    name = "ডাঃ সুমাইয়া আক্তার",
                    specialty = "পুষ্টি বিশেষজ্ঞ",
                    qualification = "BSc Nutrition, MSc Dietetics",
                    workplace = "রাজবাড়ী স্বাস্থ্য কমপ্লেক্স",
                    servicesProvided = "শিশু ও প্রাপ্তবয়স্ক পুষ্টি পরামর্শ, ওজন নিয়ন্ত্রণ, ডায়াবেটিস পুষ্টি",
                    chamber1 = "রাজবাড়ী ক্লিনিক, রাজবাড়ী",
                    mapLink = "https://maps.app.goo.gl/nutritionist-location"
                )
            )
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "🥗 পুষ্টি বিশেষজ্ঞ",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(nutritionists) { nutritionist ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = nutritionist.photoResId),
                                contentDescription = "Nutritionist Photo",
                                modifier = Modifier.size(80.dp).padding(end = 16.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Text(nutritionist.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("বিশেষজ্ঞ: ${nutritionist.specialty}", fontSize = 14.sp)
                                Text("যোগ্যতা: ${nutritionist.qualification}", fontSize = 14.sp)
                                Text("কর্মস্থল: ${nutritionist.workplace}", fontSize = 14.sp)
                                Text("পরামর্শ: ${nutritionist.servicesProvided}", fontSize = 14.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("চেম্বার:", fontWeight = FontWeight.Bold)
                        Text("১. ${nutritionist.chamber1}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(nutritionist.mapLink))
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
                        .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Nutritionist",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }

        if (showForm) {
            AddNutritionistForm(
                onNutritionistAdded = {
                    nutritionists.add(it)
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
fun AddNutritionistForm(onNutritionistAdded: (Nutritionist) -> Unit, onCancel: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var servicesProvided by remember { mutableStateOf("") }
    var chamber1 by remember { mutableStateOf("") }
    var mapLink by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> photoUri = uri }

    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text("📷 ছবি নির্বাচন করুন")
        }

        photoUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Selected Nutritionist Image",
                modifier = Modifier.size(100.dp).padding(4.dp)
            )
        }

        OutlinedTextField(name, { name = it }, label = { Text("নাম") })
        OutlinedTextField(specialty, { specialty = it }, label = { Text("বিশেষজ্ঞ বিভাগ") })
        OutlinedTextField(qualification, { qualification = it }, label = { Text("যোগ্যতা") })
        OutlinedTextField(workplace, { workplace = it }, label = { Text("কর্মস্থল") })
        OutlinedTextField(servicesProvided, { servicesProvided = it }, label = { Text("পরামর্শ বিষয়") })
        OutlinedTextField(chamber1, { chamber1 = it }, label = { Text("চেম্বার ১") })
        OutlinedTextField(mapLink, { mapLink = it }, label = { Text("Google Map লিংক") })

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onNutritionistAdded(
                        Nutritionist(
                            name = name,
                            specialty = specialty,
                            qualification = qualification,
                            workplace = workplace,
                            servicesProvided = servicesProvided,
                            chamber1 = chamber1,
                            mapLink = mapLink
                        )
                    )
                    // Reset fields
                    name = ""
                    specialty = ""
                    qualification = ""
                    workplace = ""
                    servicesProvided = ""
                    chamber1 = ""
                    mapLink = ""
                    photoUri = null
                }
            }) {
                Text("✅ Save Doctor Info")
            }

            OutlinedButton(onClick = {
                name = ""
                specialty = ""
                qualification = ""
                workplace = ""
                servicesProvided = ""
                chamber1 = ""
                mapLink = ""
                photoUri = null
                onCancel()
            }) {
                Text("❌ Cancel")
            }
        }
    }
}
