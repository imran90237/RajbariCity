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
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R

data class TeacherRequest(
    val name: String,
    val title: String,
    val subject: String,
    val days: String,
    val salary: String,
    val gender: String,
    val thana: String,
    val address: String,
    val phone: String,
    val imageUri: Uri? = null,
    val imageRes: Int = R.drawable.teacher,
    var likes: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WantToTeachPage() {
    val teacherList = remember {
        mutableStateListOf(
            TeacherRequest(
                name = "সুমি আক্তার ডেমো শিক্ষিকা",
                title = "দক্ষতার সাথে পড়ানো অভিজ্ঞতা রয়েছে\n",
                subject = "বাংলা ও ইংরেজি",
                days = "৫ দিন",
                salary = "৫০০০ টাকা",
                gender = "মহিলা",
                thana = "রাজবাড়ী সদর",
                address = "মুরালিপাড়া, রাজবাড়ী",
                phone = "০১৭xxxxxxxx",
                imageRes = R.drawable.teacher,
                likes = 3
            )
        )
    }

    var showForm by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = teacherList.filter {
        it.name.contains(searchQuery, ignoreCase = true) ||
                it.subject.contains(searchQuery, ignoreCase = true) ||
                it.thana.contains(searchQuery, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("\uD83D\uDCDA পড়াতে চাই", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("খুঁজুন (নাম/বিষয়/থানা)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredList) { teacher ->
                    TeacherCard(teacher) { teacher.likes++ }
                }
            }
        }

        FloatingActionButton(
            onClick = { showForm = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Teacher Request")
        }

        if (showForm) {
            TeacherRequestForm(
                onCancel = { showForm = false },
                onSubmit = { name, title, subject, days, salary, gender, thana, address, phone, imageUri ->
                    teacherList.add(
                        TeacherRequest(
                            name, title, subject, days, salary, gender, thana, address, phone, imageUri
                        )
                    )
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun TeacherCard(teacher: TeacherRequest, onLike: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            val painter = if (teacher.imageUri != null)
                rememberAsyncImagePainter(model = teacher.imageUri)
            else
                painterResource(id = teacher.imageRes)

            Image(
                painter = painter,
                contentDescription = "ছবি",
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(text = teacher.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("নাম: ${teacher.name}")
                Text("বিষয়: ${teacher.subject}")
                Text("লিঙ্গ: ${teacher.gender}")
                Text("দিন: ${teacher.days}, বেতন: ${teacher.salary}")
                Text("ঠিকানা: ${teacher.address}, থানা: ${teacher.thana}")
                Text("📞 ${teacher.phone}")
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "❤️ ${teacher.likes} লাইক",
                    color = Color.Red,
                    modifier = Modifier.clickable { onLike() }
                )
            }
        }
    }
}

@Composable
fun TeacherRequestForm(
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
            Text("নতুন শিক্ষক যোগ করুন\n", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                    else painterResource(id = R.drawable.teacher),
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