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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.R

data class StudentRequest(
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
    val imageRes: Int = R.drawable.student,
    var likes: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WantToStudyPage() {
    val studentList = remember {
        mutableStateListOf(
            StudentRequest(
                name = "রাফি ইসলাম ডেমো শিক্ষার্থী",
                title = "বিজ্ঞান বিষয়ে অনার্স পড়া মুসলিম ছাত্র\n",
                subject = "গণিত ও ইংরেজি",
                days = "৩ দিন",
                salary = "৩০০০ টাকা",
                gender = "পুরুষ",
                thana = "রাজবাড়ী সদর",
                address = "মুরালিপাড়া, রাজবাড়ী",
                phone = "০১৭xxxxxxxx",
                imageRes = R.drawable.student,
                likes = 2
            )
        )
    }

    var searchText by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(false) }

    val filteredList = studentList.filter {
        it.name.contains(searchText, ignoreCase = true) ||
                it.subject.contains(searchText, ignoreCase = true) ||
                it.title.contains(searchText, ignoreCase = true)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            Text("📚 পড়তে চাই/শিক্ষক চাই\n", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("সার্চ করুন") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredList) { student ->
                    StudentCard(student) {
                        student.likes++
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
                    studentList.add(
                        StudentRequest(
                            name = name,
                            title = title,
                            subject = subject,
                            days = days,
                            salary = salary,
                            gender = gender,
                            thana = thana,
                            address = address,
                            phone = phone,
                            imageUri = imageUri
                        )
                    )
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun StudentCard(student: StudentRequest, onLike: () -> Unit) {
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

            val painter = if (student.imageUri != null) {
                rememberAsyncImagePainter(model = student.imageUri)
            } else {
                painterResource(id = student.imageRes)
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

@OptIn(ExperimentalMaterial3Api::class)
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
        color = MaterialTheme.colorScheme.background.copy(alpha = 0.97f),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                Text("নতুন শিক্ষক আবেদন", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            item {
                SmallInputField("নাম", name) { name = it }
                SmallInputField("টাইটেল", title) { title = it }
                SmallInputField("বিষয়", subject) { subject = it }
                SmallInputField("সপ্তাহে কয়দিন", days) { days = it }
                SmallInputField("বেতন", salary) { salary = it }
                SmallInputField("লিঙ্গ", gender) { gender = it }
                SmallInputField("থানা", thana) { thana = it }
                SmallInputField("ঠিকানা", address) { address = it }
                SmallInputField("ফোন", phone) { phone = it }
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { launcher.launch("image/*") }
                        .padding(vertical = 8.dp)
                ) {
                    if (imageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri),
                            contentDescription = "Image",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.student),
                            contentDescription = "Default Image",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("ছবি নির্বাচন করুন", fontSize = 14.sp)
                }
            }

            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("বাতিল", color = Color.Black)
                    }

                    Button(
                        onClick = {
                            onSubmit(name, title, subject, days, salary, gender, thana, address, phone, imageUri)
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("জমা দিন")
                    }
                }
            }
        }
    }
}

@Composable
fun SmallInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    )
}
