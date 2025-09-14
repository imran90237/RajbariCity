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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.model.Nursery
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun NurseryScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getNurseries()
    }

    var showForm by remember { mutableStateOf(false) }
    val nurseries by viewModel.nurseries.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Nursery",
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
                text = "নার্সারি",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("নার্সারির নাম দিয়ে খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = nurseries.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
                items(filteredList) { nursery ->
                    NurseryCard(nursery)
                }
            }
        }

        if (showForm) {
            AddNurseryForm(
                onNurseryAdded = { newNursery ->
                    viewModel.addNursery(newNursery)
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
fun NurseryCard(nursery: Nursery) {
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
                model = nursery.photoUrl.ifBlank { R.drawable.logo },
                contentDescription = nursery.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )
            Column {
                Text(text = nursery.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "মালিক: ${nursery.owner}")
                Text(text = "ঠিকানা: ${nursery.address}")
                Text(text = "মোবাইল: ${nursery.mobile}")
            }
        }
    }
}

@Composable
fun AddNurseryForm(
    onNurseryAdded: (Nursery) -> Unit,
    onCancel: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    var name by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("নতুন নার্সারির তথ্য যোগ করুন") },
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
                OutlinedTextField(value = owner, onValueChange = { owner = it }, label = { Text("মালিকের নাম") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নম্বর") }, keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone))
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && mobile.isNotBlank()) {
                    onNurseryAdded(
                        Nursery(
                            name = name,
                            photoUrl = selectedImageUri?.toString() ?: "",
                            owner = owner,
                            address = address,
                            mobile = mobile
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