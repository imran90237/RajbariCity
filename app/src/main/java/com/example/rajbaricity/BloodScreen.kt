package com.example.rajbaricity

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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rajbaricity.model.BloodDonor
import com.example.rajbaricity.model.BloodRequest
import com.example.rajbaricity.ui.RajbariViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BloodScreen(viewModel: RajbariViewModel = viewModel()) {
    // Fetch data when the screen is first composed
    LaunchedEffect(Unit) {
        viewModel.getDonors()
        viewModel.getRequests()
    }

    var selectedTabIndex by remember { mutableStateOf(0) }
    var showDonorDialog by remember { mutableStateOf(false) }
    var showRequestDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    val donors by viewModel.donors.collectAsState()
    val requests by viewModel.requests.collectAsState()

    val filteredDonors = donors.filter {
        it.bloodGroup.contains(searchQuery.text.trim(), ignoreCase = true)
    }

    val tabTitles = listOf("রক্তদাতা", "রক্তের প্রয়োজন")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (selectedTabIndex == 0) showDonorDialog = true
                    else showRequestDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (selectedTabIndex == 0) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("রক্তের গ্রুপ দিয়ে খুঁজুন (যেমনঃ A+, O-)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            when (selectedTabIndex) {
                0 -> DonorList(filteredDonors)
                1 -> RequestList(requests)
            }
        }
    }

    if (showDonorDialog) {
        DonorInputDialog(
            onDismiss = { showDonorDialog = false },
            onAddDonor = { bloodDonor ->
                viewModel.addDonor(bloodDonor)
                showDonorDialog = false
            }
        )
    }

    if (showRequestDialog) {
        RequestInputDialog(
            onDismiss = { showRequestDialog = false },
            onAddRequest = { request ->
                viewModel.addRequest(request)
                showRequestDialog = false
            }
        )
    }
}

@Composable
fun DonorList(donors: List<BloodDonor>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(donors) { donor ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("👤 নাম: ${donor.name}")
                    Text("🩸 রক্ত: ${donor.bloodGroup}")
                    Text("📅 রক্তদানের তারিখ: ${donor.donationDate}")
                    Text("📍 ঠিকানা: ${donor.address}")
                    Text("📱 মোবাইল: ${donor.phone}")
                }
            }
        }
    }
}

@Composable
fun RequestList(requests: List<BloodRequest>) {
    if (requests.isEmpty()) {
        Text("রক্তের প্রয়োজনের কোন তথ্য পাওয়া যায়নি।", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(requests) { request ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("👤 নাম: ${request.name}")
                        Text("🩸 রক্তের গ্রুপ: ${request.bloodGroup}")
                        Text("💉 কত ব্যাগ: ${request.bagCount}")
                        Text("📅 সময়: ${request.dateTime}")
                        Text("📱 মোবাইল: ${request.phone}")
                        Text("🏥 হাসপাতাল: ${request.hospital}")
                        Text("📄 বিস্তারিত: ${request.details}")
                    }
                }
            }
        }
    }
}

@Composable
fun DonorInputDialog(onDismiss: () -> Unit, onAddDonor: (BloodDonor) -> Unit) {
    var name by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var donationDate by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && bloodGroup.isNotBlank()) {
                    onAddDonor(BloodDonor(0, name, bloodGroup, donationDate, address, phone))
                }
            }) {
                Text("সংরক্ষণ করুন")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("বাতিল করুন")
            }
        },
        title = { Text("নতুন রক্তদাতা যোগ করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") })
                OutlinedTextField(value = bloodGroup, onValueChange = { bloodGroup = it }, label = { Text("রক্তের গ্রুপ (যেমনঃ B+, O-)") })
                OutlinedTextField(value = donationDate, onValueChange = { donationDate = it }, label = { Text("রক্ত দেওয়ার তারিখ") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("মোবাইল নাম্বার") })
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun RequestInputDialog(onDismiss: () -> Unit, onAddRequest: (BloodRequest) -> Unit) {
    var name by remember { mutableStateOf("") }
    var bloodGroup by remember { mutableStateOf("") }
    var bagCount by remember { mutableStateOf("") }
    var dateTime by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var hospital by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && bloodGroup.isNotBlank()) {
                    onAddRequest(
                        BloodRequest(
                            id = 0, name, bloodGroup, bagCount, dateTime, phone, hospital, details
                        )
                    )
                }
            }) {
                Text("সাবমিট করুন")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("বাতিল করুন")
            }
        },
        title = { Text("রক্তের জন্য অনুরোধ") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") })
                OutlinedTextField(value = bloodGroup, onValueChange = { bloodGroup = it }, label = { Text("রক্তের গ্রুপ") })
                OutlinedTextField(value = bagCount, onValueChange = { bagCount = it }, label = { Text("কত ব্যাগ রক্ত লাগবে") })
                OutlinedTextField(value = dateTime, onValueChange = { dateTime = it }, label = { Text("তারিখ ও সময়") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("মোবাইল নাম্বার") })
                OutlinedTextField(value = hospital, onValueChange = { hospital = it }, label = { Text("হাসপাতালের ঠিকানা") })
                OutlinedTextField(value = details, onValueChange = { details = it }, label = { Text("বিস্তারিত লিখুন") })
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}

