package com.example.rajbaricity.AddEducation

import android.content.Intent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R
import com.example.rajbaricity.model.MadrasaInfo
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun MadrasaPage(viewModel: RajbariViewModel = viewModel()) {
    var showForm by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    val qawmiList by viewModel.qawmiMadrasas.collectAsState()
    val aliaList by viewModel.aliaMadrasas.collectAsState()

    val madrasaList = if (selectedTab == 0) qawmiList else aliaList

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("কওমি মাদ্রাসা") })
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("আলিয়া মাদ্রাসা") })
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text("🔍 মাদ্রাসা খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(madrasaList) { madrasa ->
                    MadrasaItem(
                        imageUrl = madrasa.imageUrl,
                        name = madrasa.name,
                        established = madrasa.established,
                        features = madrasa.features,
                        mapUrl = madrasa.mapUrl
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { showForm = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Madrasa")
        }

        if (showForm) {
            AddMadrasaFormScreen(
                onCancel = { showForm = false },
                onSubmit = { name, year, thana, address, phone, mapUrl, imageUri ->
                    val type = if (selectedTab == 0) "Qawmi" else "Alia"
                    val newEntry = MadrasaInfo(
                        id = 0,
                        name = name,
                        established = "স্থাপিত: $year",
                        features = "ঠিকানা: $address, ফোন: $phone, থানা: $thana",
                        mapUrl = mapUrl,
                        imageUrl = imageUri?.toString(),
                        type = type
                    )
                    viewModel.addMadrasa(newEntry)
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun MadrasaItem(
    imageUrl: String?,
    name: String,
    established: String,
    features: String,
    mapUrl: String
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = imageUrl,
                    error = painterResource(id = R.drawable.madrasa1)
                ),
                contentDescription = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = established, fontSize = 14.sp, color = Color.Gray)
            Text(text = features, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "🗺️ ম্যাপে দেখুন",
                color = Color(0xFF1E88E5),
                fontSize = 14.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun AddMadrasaFormScreen(
    onCancel: () -> Unit,
    onSubmit: (String, String, String, String, String, String, Uri?) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var thana by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var mapUrl by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.95f)),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                "📝 নতুন মাদ্রাসা যুক্ত করুন",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(onClick = { launcher.launch("image/*") }) {
                Text("📷 ছবি নির্বাচন করুন")
            }

            imageUri?.let {
                Spacer(modifier = Modifier.height(12.dp))
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("প্রতিষ্ঠানের নাম") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("স্থাপনের সাল") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = thana,
                onValueChange = { thana = it },
                label = { Text("থানা/উপজেলা") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("পূর্ণ ঠিকানা") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("ফোন নম্বর") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = mapUrl,
                onValueChange = { mapUrl = it },
                label = { Text("গুগল ম্যাপ লিংক") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onCancel) {
                    Text("❌ বাতিল")
                }
                Button(onClick = {
                    if (name.isNotBlank()) {
                        onSubmit(name, year, thana, address, phone, mapUrl, imageUri)
                    }
                }) {
                    Text("✅ সাবমিট")
                }
            }
        }
    }
}
