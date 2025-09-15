package com.example.rajbaricity.AddDiseaseSpecialist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun CancerScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getDoctors()
    }

    var showForm by remember { mutableStateOf(false) }
    val doctors by viewModel.doctors.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Doctor",
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
                text = "🎗️ ক্যান্সার বিশেষজ্ঞ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("ডাক্তারের নাম দিয়ে খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = doctors.filter {
                    it.specialty.contains("Cancer", ignoreCase = true) &&
                            it.name.contains(searchQuery, ignoreCase = true)
                }
                items(filteredList) { doctor ->
                    DoctorCard(doctor)
                }
            }
        }

        if (showForm) {
            AddDoctorForm(
                specialty = "Cancer",
                onDoctorAdded = { newDoctor ->
                    viewModel.addDoctor(newDoctor)
                    showForm = false
                },
                onCancel = {
                    showForm = false
                }
            )
        }
    }
}
