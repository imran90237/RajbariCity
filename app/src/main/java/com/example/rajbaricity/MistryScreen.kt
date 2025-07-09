package com.example.rajbaricity

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R

data class Mistry(
    val photoUri: Uri?,
    val name: String,
    val skill: String,
    val experience: String,
    val workplace: String,
    val specialtyWork: String,
    val chamber1: String,
    val chamber2: String,
    val chamber3: String,
    val phone: String
)

@Composable
fun MistryScreen() {
    var showForm by remember { mutableStateOf(false) }
    var mistrys by remember {
        mutableStateOf(
            mutableListOf(
                Mistry(
                    photoUri = null,
                    name = "মোঃ আজিজুল হক Demo",
                    skill = "বিল্ডিং মিস্ত্রি",
                    experience = "১৫ বছর অভিজ্ঞতা",
                    workplace = "রাজবাড়ী সিটি",
                    specialtyWork = "সিমেন্ট কাজ, পাথর কাজ, ইট পুতি",
                    chamber1 = "শহর এলাকার ওয়ার্কশপ",
                    chamber2 = "নিউ মার্কেট পাশে",
                    chamber3 = "রাজবাড়ী সদর",
                    phone = "01712345678"
                )
            )
        )
    }

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Mistry",
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
                text = "মিস্ত্রি ও নির্মাণ কর্মী",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("মিস্ত্রির নাম দিয়ে খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = mistrys.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
                items(filteredList) { mistry ->
                    MistryCard(mistry)
                }
            }
        }

        if (showForm) {
            AddMistryForm(
                onMistryAdded = {
                    mistrys.add(it)
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
fun MistryCard(mistry: Mistry) {
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
            val imagePainter = mistry.photoUri?.let {
                rememberAsyncImagePainter(model = it)
            } ?: painterResource(id = R.drawable.logo)

            Image(
                painter = imagePainter,
                contentDescription = mistry.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(text = mistry.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = mistry.skill)
                Text(text = mistry.experience)
                Text(text = mistry.workplace)
                Text(text = "বিশেষ কাজ: ${mistry.specialtyWork}")
                Text(text = "চেম্বার: ${mistry.chamber1}, ${mistry.chamber2}, ${mistry.chamber3}")
                Text(text = "মোবাইল: ${mistry.phone}")
            }
        }
    }
}

@Composable
fun AddMistryForm(
    onMistryAdded: (Mistry) -> Unit,
    onCancel: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    var name by remember { mutableStateOf("") }
    var skill by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var workplace by remember { mutableStateOf("") }
    var specialtyWork by remember { mutableStateOf("") }
    var chamber1 by remember { mutableStateOf("") }
    var chamber2 by remember { mutableStateOf("") }
    var chamber3 by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = selectedImageUri?.let {
                            rememberAsyncImagePainter(model = it)
                        } ?: painterResource(id = R.drawable.logo),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray)
                            .clickable { launcher.launch("image/*") },
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("মিস্ত্রির তথ্য যোগ করুন", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Button(onClick = { launcher.launch("image/*") }) {
                    Text("ছবি", fontSize = 12.sp)
                }
            }

            val fieldModifier = Modifier.fillMaxWidth().height(48.dp)
            val textStyle = TextStyle(fontSize = 13.sp)

            OutlinedTextField(name, { name = it }, label = { Text("মিস্ত্রির নাম", fontSize = 13.sp) }, modifier = fieldModifier, textStyle = textStyle)
            OutlinedTextField(skill, { skill = it }, label = { Text("দক্ষতা", fontSize = 13.sp) }, modifier = fieldModifier, textStyle = textStyle)
            OutlinedTextField(experience, { experience = it }, label = { Text("অভিজ্ঞতা", fontSize = 13.sp) }, modifier = fieldModifier, textStyle = textStyle)
            OutlinedTextField(workplace, { workplace = it }, label = { Text("কর্মস্থল", fontSize = 13.sp) }, modifier = fieldModifier, textStyle = textStyle)
            OutlinedTextField(specialtyWork, { specialtyWork = it }, label = { Text("বিশেষ কাজ", fontSize = 13.sp) }, modifier = fieldModifier, textStyle = textStyle)

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(chamber1, { chamber1 = it }, label = { Text("অবস্থান ১", fontSize = 13.sp) }, modifier = Modifier.weight(1f).height(48.dp), textStyle = textStyle)
                OutlinedTextField(chamber2, { chamber2 = it }, label = { Text("অবস্থান ২", fontSize = 13.sp) }, modifier = Modifier.weight(1f).height(48.dp), textStyle = textStyle)
            }

            OutlinedTextField(chamber3, { chamber3 = it }, label = { Text("অবস্থান ৩", fontSize = 13.sp) }, modifier = fieldModifier, textStyle = textStyle)

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("মোবাইল নম্বর", fontSize = 13.sp) },
                modifier = fieldModifier,
                textStyle = textStyle,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onCancel) {
                    Text("বাতিল করুন", fontSize = 13.sp)
                }
                Button(onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        onMistryAdded(
                            Mistry(
                                photoUri = selectedImageUri,
                                name, skill, experience, workplace,
                                specialtyWork, chamber1, chamber2, chamber3, phone
                            )
                        )
                    }
                }) {
                    Text("সংরক্ষণ করুন", fontSize = 13.sp)
                }
            }
        }
    }
}
