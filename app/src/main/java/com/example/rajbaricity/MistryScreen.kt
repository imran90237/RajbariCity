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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R
import com.example.rajbaricity.model.Mistry
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun MistryScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getMistries()
    }

    var showForm by remember { mutableStateOf(false) }
    val mistrys by viewModel.mistries.collectAsState()
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
                onMistryAdded = { newMistry ->
                    viewModel.addMistry(newMistry)
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
            AsyncImage(
                model = mistry.photoUrl.ifBlank { R.drawable.logo },
                contentDescription = mistry.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )
            Column {
                Text(text = mistry.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "দক্ষতা: ${mistry.skill}")
                Text(text = "অভিজ্ঞতা: ${mistry.experience}")
                Text(text = "কর্মস্থল: ${mistry.workplace}")
                Text(text = "বিশেষ কাজ: ${mistry.specialtyWork}")
                Text(text = "চেম্বার: ${mistry.chamber1}")
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
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("নতুন মিস্ত্রির তথ্য যোগ করুন") },
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
                OutlinedTextField(value = skill, onValueChange = { skill = it }, label = { Text("দক্ষতা") })
                OutlinedTextField(value = experience, onValueChange = { experience = it }, label = { Text("অভিজ্ঞতা") })
                OutlinedTextField(value = workplace, onValueChange = { workplace = it }, label = { Text("কর্মস্থল") })
                OutlinedTextField(value = specialtyWork, onValueChange = { specialtyWork = it }, label = { Text("বিশেষ কাজ") })
                OutlinedTextField(value = chamber1, onValueChange = { chamber1 = it }, label = { Text("চেম্বার") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("মোবাইল নম্বর") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone))
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && phone.isNotBlank()) {
                    onMistryAdded(
                        Mistry(
                            name = name,
                            photoUrl = selectedImageUri?.toString() ?: "",
                            skill = skill,
                            experience = experience,
                            workplace = workplace,
                            specialtyWork = specialtyWork,
                            chamber1 = chamber1,
                            phone = phone
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


