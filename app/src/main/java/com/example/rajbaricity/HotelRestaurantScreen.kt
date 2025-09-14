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
import com.example.rajbaricity.model.HotelRestaurant
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun HotelRestaurantScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getHotelRestaurants()
    }

    var showForm by remember { mutableStateOf(false) }
    val newHotelRestaurants by viewModel.hotelRestaurants.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val staticHotelList = remember {
        listOf(
            HotelRestaurant(name = "হোটেল নাজমা", menu = "", address = "রাজবাড়ী সদর, রাজবাড়ী", phone = "০১৭২৪৬৫১২৩৪"),
            HotelRestaurant(name = "হোটেল লতিফ আবাসিক", menu = "", address = "পাংশা, রাজবাড়ী", phone = "০১৭৬৮৩৬৪৩৫৮"),
            HotelRestaurant(name = "হোটেল গোল্ডেন", menu = "", address = "রাজবাড়ী সদর, রাজবাড়ী", phone = "০১৭১৫১৩২৬৭৪"),
            HotelRestaurant(name = "হোটেল সেভেনটি ওয়ান আবাসিক এন্ড রিসোর্ট", menu = "", address = "রাজবাড়ী সদর, রাজবাড়ী", phone = "০১৭৬৩৭৭৩৮৭১"),
            HotelRestaurant(name = "পালকি রেস্টুরেন্ট এন্ড রেস্তোরাঁ", menu = "", address = "রাজবাড়ী সদর, রাজবাড়ী", phone = "০১৮৭২১৭৩৪৮২"),
            HotelRestaurant(name = "নিরালা হোটেল", menu = "মোঃ জলিল ফকির", address = "", phone = ""),
            HotelRestaurant(name = "মোঃ মোহন ফকির হোটেল", menu = "মোঃ মোহন ফকির", address = "দৌলতদিয়া স্টেশন বাজার, গোয়ালন্দ", phone = ""),
            HotelRestaurant(name = "গোয়ালন্দ ডাকবাংলো", menu = "জেলা পরিষদ, রাজবাড়ী", address = "গোয়ালন্দ, রাজবাড়ী", phone = "০৬৪২৩-৫৬১০৩"),
            HotelRestaurant(name = "বালিয়াকান্দি ডাক বাংলো", menu = "জেলা পরিষদ, রাজবাড়ী", address = "বালিয়াকান্দি, রাজবাড়ী", phone = "০৬৪২২-৫৬০০১"),
            HotelRestaurant(name = "হোটেল লতিফ আবাসিক", menu = "খান মোঃ আব্দুল হাই", address = "টেশনরোড, পাংশা", phone = "০১৭১৬৯৯১১০৬"),
            HotelRestaurant(name = "পাংশা ডাকবাংলো", menu = "জেলা পরিষদ, রাজবাড়ী", address = "পাংশা, রাজবাড়ী", phone = "০৬৪২৪-৭৫০৩৫"),
            HotelRestaurant(name = "রাজবাড়ী বোডিং", menu = "রতন পোদ্দার", address = "সাং-কলেজপাড়া, বিনোদপুর, রাজবাড়ী", phone = "০১১৯১৫৭১৭৪৩"),
            HotelRestaurant(name = "মিড টাউন হোটেল", menu = "মোঃ নুরুজ্জামান", address = "সাং-বেড়াডাঙ্গা, রাজবাড়ী পৌরসভা", phone = "০১১৯০৬৯০৩০১"),
            HotelRestaurant(name = "প্রাইম হোটেল", menu = "মোঃ ছিদ্দিকুর রহমান", address = "সাং-বেড়াডাঙ্গা, রাজবাড়ী পৌরসভা", phone = "০১৯১৩৯৫৯২২২"),
            HotelRestaurant(name = "গুলশান বোডিং", menu = "আঃ কাদের প্রামানিক", address = "সাং-সজ্জনকান্দা, রাজবাড়ী পৌরসভা", phone = "০১৯২৩১৯৩১৩৮"),
            HotelRestaurant(name = "হোটেল পার্ক ৩য় তলা আবাসিক", menu = "সাবিত্রি রাণী চক্রবর্ত্তী", address = "সজ্জনকান্দা, রাজবাড়ী পৌরসভা", phone = "০৬৪১-৬৬১১১")
        )
    }

    val combinedList = staticHotelList + newHotelRestaurants

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showForm = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "হোটেল ও রেস্টুরেন্ট তালিকা",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("হোটেলের নাম দিয়ে খুঁজুন") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                val filteredList = combinedList.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
                items(filteredList) { hotel ->
                    HotelRestaurantCard(hotel)
                }
            }
        }

        if (showForm) {
            AddHotelRestaurantForm(
                onHotelRestaurantAdded = { newHotel ->
                    viewModel.addHotelRestaurant(newHotel)
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
fun HotelRestaurantCard(hotel: HotelRestaurant) {
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
                model = (hotel.photoUrl ?: "").ifBlank { R.drawable.logo },
                contentDescription = hotel.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )
            Column {
                Text(text = hotel.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                if (!hotel.menu.isNullOrBlank()) Text("মালিক: ${hotel.menu}")
                if (!hotel.address.isNullOrBlank()) Text("ঠিকানা: ${hotel.address}")
                if (!hotel.phone.isNullOrBlank()) Text("ফোন: ${hotel.phone}")
            }
        }
    }
}

@Composable
fun AddHotelRestaurantForm(
    onHotelRestaurantAdded: (HotelRestaurant) -> Unit,
    onCancel: () -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    var name by remember { mutableStateOf("") }
    var menu by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("নতুন হোটেল/রেস্টুরেন্ট যোগ করুন") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("নাম") })
                OutlinedTextField(value = menu, onValueChange = { menu = it }, label = { Text("মালিক (ঐচ্ছিক)") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("ফোন নম্বর") })
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank()) {
                    onHotelRestaurantAdded(
                        HotelRestaurant(
                            name = name,
                            menu = menu,
                            address = address,
                            phone = phone,
                            photoUrl = selectedImageUri?.toString() ?: ""
                        )
                    )
                }
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