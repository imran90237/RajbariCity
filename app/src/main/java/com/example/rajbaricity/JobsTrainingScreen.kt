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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ✅ JobInfo Data Class
data class JobInfo(
    val title: String,
    val organization: String,
    val position: String,
    val numberOfPosts: String,
    val qualification: String,
    val experience: String,
    val thana: String,
    val address: String,
    val salary: String,
    val phone: String,
    val email: String,
    val deadline: String,
    val details: String
)

// ✅ TrainingInfo Data Class
data class TrainingInfo(
    val title: String,
    val organization: String,
    val qualification: String,
    val duration: String,
    val thana: String,
    val address: String,
    val fee: String,
    val phone: String,
    val email: String,
    val deadline: String,
    val details: String
)

@Composable
fun JobsTrainingScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("চাকরি", "প্রশিক্ষণ")

    var showJobDialog by remember { mutableStateOf(false) }
    var showTrainingDialog by remember { mutableStateOf(false) }

    var jobList by remember { mutableStateOf(listOf<JobInfo>()) }
    var trainingList by remember { mutableStateOf(listOf<TrainingInfo>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("💼 চাকরি ও প্রশিক্ষণ", style = MaterialTheme.typography.titleLarge)

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index }) {
                    Text(title, modifier = Modifier.padding(16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (selectedTab == 0) showJobDialog = true else showTrainingDialog = true
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(4.dp))
            Text("নতুন যোগ করুন")
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (selectedTab == 0) {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(jobList) { job ->
                    JobsTrainingCard(
                        title = job.title,
                        description = "${job.organization} এ ${job.position}, আবেদন শেষ: ${job.deadline}"
                    )
                }
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(trainingList) { training ->
                    JobsTrainingCard(
                        title = training.title,
                        description = "${training.organization}, ${training.duration} প্রশিক্ষণ, শেষ তারিখ: ${training.deadline}"
                    )
                }
            }
        }

        if (showJobDialog) {
            JobFormDialog(
                onDismiss = { showJobDialog = false },
                onSubmit = {
                    jobList = jobList + it
                    showJobDialog = false
                }
            )
        }

        if (showTrainingDialog) {
            TrainingFormDialog(
                onDismiss = { showTrainingDialog = false },
                onSubmit = {
                    trainingList = trainingList + it
                    showTrainingDialog = false
                }
            )
        }
    }
}

// ✅ Common Card UI
@Composable
fun JobsTrainingCard(
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 18.sp, color = Color(0xFF2E7D32))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

// ✅ Job Form Dialog
@Composable
fun JobFormDialog(
    onDismiss: () -> Unit,
    onSubmit: (JobInfo) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var organization by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var posts by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var thana by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSubmit(
                    JobInfo(
                        title, organization, position, posts,
                        qualification, experience, thana, address,
                        salary, phone, email, deadline, details
                    )
                )
            }) {
                Text("সাবমিট করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল করুন")
            }
        },
        title = { Text("নতুন চাকরি তথ্য") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("জব টাইটেল") })
                OutlinedTextField(value = organization, onValueChange = { organization = it }, label = { Text("প্রতিষ্ঠান") })
                OutlinedTextField(value = position, onValueChange = { position = it }, label = { Text("পদের নাম") })
                OutlinedTextField(value = posts, onValueChange = { posts = it }, label = { Text("পদের সংখ্যা") })
                OutlinedTextField(value = qualification, onValueChange = { qualification = it }, label = { Text("যোগ্যতা") })
                OutlinedTextField(value = experience, onValueChange = { experience = it }, label = { Text("অভিজ্ঞতা") })
                OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = salary, onValueChange = { salary = it }, label = { Text("বেতন") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("ইমেইল") })
                OutlinedTextField(value = deadline, onValueChange = { deadline = it }, label = { Text("শেষ তারিখ") })
                OutlinedTextField(value = details, onValueChange = { details = it }, label = { Text("বিস্তারিত") })
            }
        }
    )
}

// ✅ Training Form Dialog
@Composable
fun TrainingFormDialog(
    onDismiss: () -> Unit,
    onSubmit: (TrainingInfo) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var organization by remember { mutableStateOf("") }
    var qualification by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var thana by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onSubmit(
                    TrainingInfo(
                        title, organization, qualification, duration,
                        thana, address, fee, phone, email, deadline, details
                    )
                )
            }) {
                Text("সাবমিট করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল করুন")
            }
        },
        title = { Text("নতুন প্রশিক্ষণ তথ্য") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("টাইটেল") })
                OutlinedTextField(value = organization, onValueChange = { organization = it }, label = { Text("প্রতিষ্ঠান") })
                OutlinedTextField(value = qualification, onValueChange = { qualification = it }, label = { Text("যোগ্যতা") })
                OutlinedTextField(value = duration, onValueChange = { duration = it }, label = { Text("সময়কাল") })
                OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = fee, onValueChange = { fee = it }, label = { Text("ফি") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন") })
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("ইমেইল") })
                OutlinedTextField(value = deadline, onValueChange = { deadline = it }, label = { Text("শেষ তারিখ") })
                OutlinedTextField(value = details, onValueChange = { details = it }, label = { Text("বিস্তারিত") })
            }
        }
    )
}
