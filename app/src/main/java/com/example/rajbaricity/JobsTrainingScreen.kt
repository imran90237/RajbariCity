package com.example.rajbaricity

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rajbaricity.model.JobsTraining
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun JobsTrainingScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getJobsTrainings()
    }

    var showForm by remember { mutableStateOf(false) }
    val jobsTrainings by viewModel.jobsTrainings.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("চাকরি", "প্রশিক্ষণ")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Job/Training",
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
                text = "💼 চাকরি ও প্রশিক্ষণ",
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
                label = { Text("চাকরি বা প্রশিক্ষণ খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = jobsTrainings.filter {
                    val matchTab = if (selectedTab == 0) it.isJob else !it.isJob
                    val matchSearch = it.title.contains(searchQuery, ignoreCase = true) ||
                            it.organization.contains(searchQuery, ignoreCase = true)
                    matchTab && matchSearch
                }
                items(filteredList) { item ->
                    JobsTrainingCard(item)
                }
            }
        }

        if (showForm) {
            AddJobsTrainingForm(
                onItemAdded = { newItem ->
                    viewModel.addJobsTraining(newItem)
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
fun JobsTrainingCard(item: JobsTraining) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = item.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "প্রতিষ্ঠান: ${item.organization}")
            if (item.isJob) {
                Text(text = "পদের নাম: ${item.position}")
                Text(text = "বেতন: ${item.salary}")
            } else {
                Text(text = "সময়কাল: ${item.duration}")
                Text(text = "ফি: ${item.fee}")
            }
            Text(text = "শেষ তারিখ: ${item.deadline}")
        }
    }
}

@Composable
fun AddJobsTrainingForm(
    onItemAdded: (JobsTraining) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var organization by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var thana by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var isJob by remember { mutableStateOf(true) }

    // Job specific fields
    var position by remember { mutableStateOf("") }
    var numberOfPosts by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }

    // Training specific fields
    var duration by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(if (isJob) "নতুন চাকরির তথ্য" else "নতুন প্রশিক্ষণের তথ্য") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = isJob, onClick = { isJob = true })
                    Text("চাকরি", modifier = Modifier.padding(end = 16.dp))
                    RadioButton(selected = !isJob, onClick = { isJob = false })
                    Text("প্রশিক্ষণ")
                }
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("টাইটেল") })
                OutlinedTextField(value = organization, onValueChange = { organization = it }, label = { Text("প্রতিষ্ঠান") })
                OutlinedTextField(value = qualification, onValueChange = { qualification = it }, label = { Text("যোগ্যতা") })
                if (isJob) {
                    OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("পদের নাম") })
                    OutlinedTextField(value = numberOfPosts, onValueChange = { numberOfPosts = it }, label = { Text("পদের সংখ্যা") })
                    OutlinedTextField(value = experience, onValueChange = { experience = it }, label = { Text("অভিজ্ঞতা") })
                    OutlinedTextField(value = salary, onValueChange = { salary = it }, label = { Text("বেতন") })
                } else {
                    OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("সময়কাল") })
                    OutlinedTextField(value = fee, onValueChange = { fee = it }, label = { Text("ফি") })
                }
                OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("ইমেইল") })
                OutlinedTextField(value = deadline, onValueChange = { deadline = it }, label = { Text("শেষ তারিখ") })
                OutlinedTextField(value = details, onValueChange = { details = it }, label = { Text("বিস্তারিত") })
            }
        },
        confirmButton = {
            Button(onClick = {
                val newItem = JobsTraining(
                    title = title,
                    organization = organization,
                    qualification = qualification,
                    thana = thana,
                    address = address,
                    phone = phone,
                    email = email,
                    deadline = deadline,
                    details = details,
                    isJob = isJob,
                    position = if (isJob) position else "",
                    numberOfPosts = if (isJob) numberOfPosts else "",
                    experience = if (isJob) experience else "",
                    salary = if (isJob) salary else "",
                    duration = if (!isJob) duration else "",
                    fee = if (!isJob) fee else ""
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