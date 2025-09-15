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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.model.LostAndFound
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun LostAndFoundScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getLostAndFounds()
    }

    var showForm by remember { mutableStateOf(false) }
    val lostAndFounds by viewModel.lostAndFounds.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("হারিয়েছে", "পেয়েছে")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Lost/Found Item",
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
                text = "🧳 হারানো এবং পাওয়া",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    ) {
                        Text(title, modifier = Modifier.padding(16.dp))
                    }
                }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("হারানো বা পাওয়া জিনিস খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = lostAndFounds.filter {
                    val matchTab = if (selectedTab == 0) it.isLost else !it.isLost
                    val matchSearch = it.title.contains(searchQuery, ignoreCase = true) ||
                            it.description.contains(searchQuery, ignoreCase = true)
                    matchTab && matchSearch
                }
                items(filteredList) { item ->
                    LostAndFoundCard(item)
                }
            }
        }

        if (showForm) {
            AddLostAndFoundForm(
                onItemAdded = { newItem ->
                    viewModel.addLostAndFound(newItem)
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
fun LostAndFoundCard(item: LostAndFound) {
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
                model = item.photoUrl.ifBlank { R.drawable.logo },
                contentDescription = item.title,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )
            Column {
                Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "বিবরণ: ${item.description}")
                Text(text = "যোগাযোগ: ${item.contactName} (${item.contactPhone})")
            }
        }
    }
}

@Composable
fun AddLostAndFoundForm(
    onItemAdded: (LostAndFound) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var contactName by remember { mutableStateOf("") }
    var contactPhone by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isLost by remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(if (isLost) "হারানো জিনিসের তথ্য" else "পাওয়া জিনিসের তথ্য") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = isLost, onClick = { isLost = true })
                    Text("হারিয়েছে", modifier = Modifier.padding(end = 16.dp))
                    RadioButton(selected = !isLost, onClick = { isLost = false })
                    Text("পেয়েছে")
                }

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

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("শিরোনাম") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("বিবরণ") })
                OutlinedTextField(value = contactName, onValueChange = { contactName = it }, label = { Text("যোগাযোগের নাম") })
                OutlinedTextField(value = contactPhone, onValueChange = { contactPhone = it }, label = { Text("যোগাযোগের ফোন") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val newItem = LostAndFound(
                    title = title,
                    description = description,
                    contactName = contactName,
                    contactPhone = contactPhone,
                    photoUrl = selectedImageUri?.toString() ?: "",
                    isLost = isLost
                )
                onItemAdded(newItem)
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