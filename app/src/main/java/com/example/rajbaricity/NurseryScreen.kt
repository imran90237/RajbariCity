package com.example.rajbaricity

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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

// ✅ Model
data class Nursery(
    val name: String,
    val owner: String,
    val address: String,
    val mobile: String,
    val imageUri: Uri? = null
)

@Composable
fun NurseryScreen() {
    var showForm by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    var nurseryList by remember {
        mutableStateOf(
            listOf(
                Nursery("গ্রিন নার্সারি", "আলী হোসেন", "সিলেট সদর", "01712345678"),
                Nursery("নেচার গার্ডেন", "রহিম উদ্দিন", "জিন্দাবাজার", "01887654321")
            )
        )
    }

    val filteredList = nurseryList.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.owner.contains(searchQuery, ignoreCase = true) ||
                it.address.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("🌿 নার্সারি", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("🔍 সার্চ করুন") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))

            LazyColumn {
                items(filteredList) { nursery ->
                    NurseryCard(nursery)
                }
            }

            if (showForm) {
                AddNurseryDialog(
                    onDismiss = { showForm = false },
                    onSubmit = { newNursery ->
                        nurseryList = nurseryList + newNursery
                        showForm = false
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { showForm = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Nursery")
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
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = nursery.imageUri?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.logo),
                contentDescription = "Nursery Image",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(nursery.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("👤 মালিক: ${nursery.owner}")
                Text("📍 ঠিকানা: ${nursery.address}")
                Text("📞 মোবাইল: ${nursery.mobile}")
            }
        }
    }
}

@Composable
fun AddNurseryDialog(
    onDismiss: () -> Unit,
    onSubmit: (Nursery) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Image picker launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && owner.isNotBlank() && mobile.isNotBlank()) {
                        val nursery = Nursery(
                            name = name,
                            owner = owner,
                            address = address,
                            mobile = mobile,
                            imageUri = selectedImageUri
                        )
                        onSubmit(nursery)
                    }
                }
            ) {
                Text("✅ সাবমিট")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("❌ বাতিল")
            }
        },
        title = { Text("🪴 নতুন নার্সারি যোগ করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                // 👉 Launch image picker
                                imagePickerLauncher.launch("image/*")
                            },
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                Icons.Default.AddPhotoAlternate,
                                contentDescription = "Select Photo",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    Image(
                        painter = selectedImageUri?.let { rememberAsyncImagePainter(it) }
                            ?: painterResource(id = R.drawable.logo),
                        contentDescription = "Preview",
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }

                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নার্সারির নাম") })
                OutlinedTextField(value = owner, onValueChange = { owner = it }, label = { Text("মালিকের নাম") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নাম্বার") })
            }
        }
    )
}
