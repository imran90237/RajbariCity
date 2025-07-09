// File: EducationScreen.kt
package com.example.rajbaricity

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EducationScreen(
    onNavigateTo: (String) -> Unit
) {
    val categories = listOf(
        "\uD83C\uDFEB স্কুল" to "school",
        "\uD83C\uDF93 কলেজ" to "college",
        "\uD83D\uDD4C মাদ্রাসা" to "madrasa",
        "\uD83D\uDC65 কোচিং সেন্টার" to "coaching",
        "\uD83D\uDCD6 িক্ষক চাই" to "want_to_study",
        "✍\uFE0F পড়াতে চাই" to "want_to_teach"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "📚 শিক্ষা সম্পর্কিত তথ্য",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(categories) { (title, route) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp) // আগে ছিল 100.dp, এখন বাড়ানো হলো
                        .clickable { onNavigateTo(route) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📘", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            title,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}







//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Add
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun EducationScreen(onHomeClick: () -> Unit = {}) {
//    val tabs = listOf("স্কুল", "কলেজ", "মাদ্রাসা", "কোচিং সেন্টার", "পড়তে চাই", "পড়াতে চাই")
//    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
//    var showForm by rememberSaveable { mutableStateOf(false) }
//    var searchQuery by rememberSaveable { mutableStateOf("") }
//
//    val educationData = remember {
//        mutableStateListOf(
//            mutableStateListOf(
//                "রাজবাড়ী সরকারি উচ্চ বিদ্যালয়" to "স্থাপিত: ১৮৯০\nমাধ্যমিক স্তরের শ্রেষ্ঠ প্রতিষ্ঠান।",
//                "রাজবাড়ী সরকারি বালিকা উচ্চ বিদ্যালয়" to "স্থাপিত: ১৯৬১\nমাধ্যমিক স্তরের শ্রেষ্ঠ প্রতিষ্ঠান।"
//            ),
//            mutableStateListOf(
//                "রাজবাড়ী সরকারি কলেজ" to "স্নাতক ও স্নাতকোত্তর কোর্স, জাতীয় বিশ্ববিদ্যালয় অনুমোদিত।"
//            ),
//            mutableStateListOf(
//                "দারুল উলুম ভাজনচালা হাফিজিয়া কওমিয়া মাদ্রাসা" to "কওমী স্তরের শিক্ষা প্রদান করে।"
//            ),
//            mutableStateListOf(
//                "নিউ হরাইজন কোচিং সেন্টার" to "SSC ও HSC পরীক্ষার্থীদের জন্য বিশেষ কোচিং।"
//            ),
//            mutableStateListOf(), // পড়তে চাই (student requests)
//            mutableStateListOf()  // পড়াতে চাই (teacher offers)
//        )
//    }
//
//    if (showForm) {
//        when (selectedTabIndex) {
//            in 0..3 -> {
//                EducationFormScreen(
//                    onCancel = { showForm = false },
//                    onSubmit = { name, year, thana, address, phone ->
//                        val info = "স্থাপিত: $year | ঠিকানা: $thana, $address | ফোন: $phone"
//                        educationData[selectedTabIndex].add(0, name to info)
//                        showForm = false
//                    }
//                )
//            }
//            4 -> {
//                StudentRequestForm(
//                    onCancel = { showForm = false },
//                    onSubmit = { name, title, subject, days, salary, gender, thana, address, phone ->
//                        val info = "$title\nবিষয়: $subject\nসপ্তাহে $days দিন\nবেতন: $salary\nলিঙ্গ: $gender\n$thana, $address\nফোন: $phone"
//                        educationData[4].add(0, name to info)
//                        showForm = false
//                    }
//                )
//            }
//            5 -> {
//                TeacherOfferForm(
//                    onCancel = { showForm = false },
//                    onSubmit = { name, title, subject, days, salary, thana, address, phone, gender, photoUri ->
//                        val info = "$title\nবিষয়: $subject\nসপ্তাহে $days দিন\nবেতন: $salary\nলিঙ্গ: $gender\n$thana, $address\nফোন: $phone\nছবি: ${photoUri?.lastPathSegment ?: "না"}"
//                        educationData[5].add(0, name to info)
//                        showForm = false
//                    }
//                )
//            }
//        }
//    } else {
//        Box(modifier = Modifier.fillMaxSize()) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                ScrollableTabRow(selectedTabIndex = selectedTabIndex, edgePadding = 16.dp) {
//                    tabs.forEachIndexed { index, title ->
//                        Tab(
//                            selected = selectedTabIndex == index,
//                            onClick = { selectedTabIndex = index },
//                            text = { Text(title, fontSize = 14.sp) }
//                        )
//                    }
//                }
//
//                SearchBar(
//                    query = searchQuery,
//                    onQueryChanged = { searchQuery = it },
//                    totalCount = getFilteredCount(educationData[selectedTabIndex], searchQuery)
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                EducationTabContent(
//                    query = searchQuery,
//                    data = educationData[selectedTabIndex]
//                )
//
//                Spacer(modifier = Modifier.height(80.dp))
//
//
//            }
//
//            FloatingActionButton(
//                onClick = { showForm = true },
//                shape = CircleShape,
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = Color.White,
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(16.dp)
//            ) {
//                Icon(Icons.Default.Add, contentDescription = "নতুন যুক্ত করুন", modifier = Modifier.size(30.dp))
//            }
//        }
//    }
//}
//
//private fun getFilteredCount(data: List<Pair<String, String>>, query: String): Int {
//    return data.count { it.first.contains(query.trim(), ignoreCase = true) }
//}
//
//@Composable
//fun EducationTabContent(query: String, data: List<Pair<String, String>>) {
//    val filteredList = data.filter { it.first.contains(query.trim(), ignoreCase = true) }
//
//    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
//        items(filteredList) { (title, desc) ->
//            EducationCard(title = title, description = desc)
//        }
//    }
//}
//
//@Composable
//fun EducationCard(title: String, description: String) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = title, fontSize = 18.sp, color = Color(0xFF333333))
//            Spacer(modifier = Modifier.height(6.dp))
//            Text(text = description, fontSize = 14.sp, color = Color.DarkGray)
//        }
//    }
//}
//
//@Composable
//fun EducationFormScreen(
//    onCancel: () -> Unit,
//    onSubmit: (String, String, String, String, String) -> Unit
//) {
//    var name by remember { mutableStateOf("") }
//    var year by remember { mutableStateOf("") }
//    var thana by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//    var imageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        imageUri = uri
//    }
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("📝 নতুন তথ্য যুক্ত করুন", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
//
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text("📸 ছবি যোগ করুন")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("প্রতিষ্ঠানের নাম") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("স্থাপনের সাল") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা/উপজেলা") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("পূর্ণ ঠিকানা") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন নম্বর") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            OutlinedButton(onClick = onCancel) { Text("❌ বাতিল") }
//            Button(onClick = {
//                if (name.isNotBlank()) {
//                    // You could also pass imageUri if you want to save the image Uri along with data
//                    onSubmit(name, year, thana, address, phone)
//                }
//            }) {
//                Text("✅ সাবমিট")
//            }
//        }
//    }
//}
//
//@Composable
//fun StudentRequestForm(
//    onCancel: () -> Unit,
//    onSubmit: (String, String, String, String, String, String, String, String, String) -> Unit
//) {
//    var name by remember { mutableStateOf("") }
//    var title by remember { mutableStateOf("") }
//    var subject by remember { mutableStateOf("") }
//    var days by remember { mutableStateOf("") }
//    var salary by remember { mutableStateOf("") }
//    var gender by remember { mutableStateOf("") }
//    var thana by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("পড়তে চাওয়ার তথ্য দিন", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
//
//        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("টাইটেল ") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("বিষয়") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = days, onValueChange = { days = it }, label = { Text("সপ্তাহে কত দিন") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = salary, onValueChange = { salary = it }, label = { Text("বেতন") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("লিঙ্গ") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা/উপজেলা") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন নম্বর") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            OutlinedButton(onClick = onCancel) { Text("❌ বাতিল") }
//            Button(onClick = {
//                if (name.isNotBlank()) {
//                    onSubmit(name, title, subject, days, salary, gender, thana, address, phone)
//                }
//            }) {
//                Text("✅ সাবমিট")
//            }
//        }
//    }
//}
//
//@Composable
//fun TeacherOfferForm(
//    onCancel: () -> Unit,
//    onSubmit: (String, String, String, String, String, String, String, String, String, Uri?) -> Unit
//) {
//    var name by remember { mutableStateOf("") }
//    var title by remember { mutableStateOf("") }
//    var subject by remember { mutableStateOf("") }
//    var days by remember { mutableStateOf("") }
//    var salary by remember { mutableStateOf("") }
//    var thana by remember { mutableStateOf("") }
//    var address by remember { mutableStateOf("") }
//    var phone by remember { mutableStateOf("") }
//    var gender by remember { mutableStateOf("") }
//    var photoUri by remember { mutableStateOf<Uri?>(null) }
//
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        photoUri = uri
//    }
//
//    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        Text("পড়াতে চাওয়ার তথ্য দিন", fontSize = 20.sp, modifier = Modifier.padding(bottom = 16.dp))
//
//        Button(onClick = { launcher.launch("image/*") }) {
//            Text("ছবি যোগ করুন")
//        }
//        photoUri?.let {
//            Text("ছবি যোগ হয়েছে: ${it.lastPathSegment ?: ""}", fontSize = 12.sp, color = Color.Gray)
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("টাইটেল") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("বিষয়") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = days, onValueChange = { days = it }, label = { Text("সপ্তাহে কত দিন") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = salary, onValueChange = { salary = it }, label = { Text("বেতন") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("লিঙ্গ") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = thana, onValueChange = { thana = it }, label = { Text("থানা/উপজেলা") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন নম্বর") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            OutlinedButton(onClick = onCancel) { Text("❌ বাতিল") }
//            Button(onClick = {
//                if (name.isNotBlank()) {
//                    onSubmit(name, title, subject, days, salary, thana, address, phone, gender, photoUri)
//                }
//            }) {
//                Text("✅ সাবমিট")
//            }
//        }
//    }
//}
//
//@Composable
//fun SearchBar(
//    query: String,
//    onQueryChanged: (String) -> Unit,
//    totalCount: Int
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        OutlinedTextField(
//            value = query,
//            onValueChange = onQueryChanged,
//            label = { Text("সার্চ করুন") },
//            modifier = Modifier.weight(1f),
//            shape = MaterialTheme.shapes.large // Rounded corner
//        )
//
//        Spacer(modifier = Modifier.width(8.dp))
//
//        // Rounded count box
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .background(color = Color(0xFFF0F0F0), shape = MaterialTheme.shapes.large)
//                .padding(horizontal = 12.dp, vertical = 8.dp)
//        ) {
//            Text(
//                text = "$totalCount টি",
//                fontSize = 14.sp,
//                color = Color.DarkGray
//            )
//        }
//    }
//}
//
