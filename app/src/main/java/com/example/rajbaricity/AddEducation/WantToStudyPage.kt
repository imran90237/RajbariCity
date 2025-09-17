package com.example.rajbaricity.AddEducation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R
import com.example.rajbaricity.model.Student
import com.example.rajbaricity.ui.RajbariViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WantToStudyPage(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getStudents()
    }
    val studentList by viewModel.students.collectAsState()
    var showForm by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val filteredList = studentList.filter {
        it.name.contains(searchText, ignoreCase = true) ||
                it.subject.contains(searchText, ignoreCase = true) ||
                it.title.contains(searchText, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text("📚 পড়তে চাই\n", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("সার্চ করুন") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredList) {
                    StudentCard(it) {
                        viewModel.likeStudent(it.id)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showForm = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Student Request")
        }

        if (showForm) {
            StudentRequestForm(
                onCancel = { showForm = false },
                onSubmit = { name, title, subject, days, salary, gender, thana, address, phone, imageUri ->
                    val newStudent = Student(
                        name = name,
                        title = title,
                        subject = subject,
                        days = days,
                        salary = salary,
                        gender = gender,
                        thana = thana,
                        address = address,
                        phone = phone,
                        imageUrl = imageUri?.toString() // Passing imageUri as string
                    )
                    viewModel.addStudent(newStudent)
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun StudentCard(student: Student, onLike: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)) {

            val painter = if (student.imageUrl != null) {
                rememberAsyncImagePainter(model = student.imageUrl)
            } else {
                painterResource(id = R.drawable.student)
            }

            Image(
                painter = painter,
                contentDescription = "ছবি",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = student.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "নাম: ${student.name}")
                Text(text = "বিষয়: ${student.subject}")
                Text(text = "লিঙ্গ: ${student.gender}")
                Text(text = "দিন: ${student.days}, বেতন: ${student.salary}")
                Text(text = "ঠিকানা: ${student.address}, থানা: ${student.thana}")
                Text(text = "📞 ${student.phone}")

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "❤️ ${student.likes} লাইক",
                    color = Color.Red,
                    modifier = Modifier.clickable { onLike() }
                )
            }
        }
    }
}

@Composable
fun StudentRequestForm(
    onCancel: () -> Unit,
    onSubmit: (
        name: String, title: String, subject: String, days: String,
        salary: String, gender: String, thana: String, address: String,
        phone: String, imageUri: Uri?
    ) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("পুরুষ") }
    var thana by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("নতুন ছাত্র/ছাত্রী যোগ করুন", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Two-column layout
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("বিষয়") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = salary, onValueChange = { salary = it }, label = { Text("বেতন") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }

                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("টাইটেল") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = days, onValueChange = { days = it }, label = { Text("দিন") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("লিঙ্গ") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Image Picker
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { launcher.launch("image/*") }
                    .padding(vertical = 6.dp)
            ) {
                Image(
                    painter = if (imageUri != null) rememberAsyncImagePainter(imageUri)
                    else painterResource(id = R.drawable.student),
                    contentDescription = "ছবি",
                    modifier = Modifier.size(70.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("ছবি যোগ করুন", color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onCancel) { Text("বাতিল") }
                Spacer(modifier = Modifier.width(12.dp))
                Button(
                    onClick = {
                        onSubmit(name, title, subject, days, salary, gender, thana, address, phone, imageUri)
                    },
                    enabled = name.isNotBlank() && title.isNotBlank()
                ) { Text("সাবমিট") }
            }
        }
    }
}

