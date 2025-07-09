package com.example.rajbaricity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// হোটেল/রেস্টুরেন্টের তথ্যের ডেটা ক্লাস
data class HotelRestaurant(
    val name: String,
    val menu: String,
    val address: String,
    val phone: String
)

@Composable
fun HotelRestaurantScreen(onHomeClick: () -> Unit = {}) {
    var hotelList by remember {
        mutableStateOf(
            listOf(
                HotelRestaurant("হোটেল নাজমা", "", "রাজবাড়ী সদর, রাজবাড়ী", "০১৭২৪৬৫১২৩৪"),
                HotelRestaurant("হোটেল লতিফ আবাসিক", "", "পাংশা, রাজবাড়ী", "০১৭৬৮৩৬৪৩৫৮"),
                HotelRestaurant("হোটেল গোল্ডেন", "", "রাজবাড়ী সদর, রাজবাড়ী", "০১৭১৫১৩২৬৭৪"),
                HotelRestaurant("হোটেল সেভেনটি ওয়ান আবাসিক এন্ড রিসোর্ট", "", "রাজবাড়ী সদর, রাজবাড়ী", "০১৭৬৩৭৭৩৮৭১"),
                HotelRestaurant("পালকি রেস্টুরেন্ট এন্ড রেস্তোরাঁ", "", "রাজবাড়ী সদর, রাজবাড়ী", "০১৮৭২১৭৩৪৮২"),
                HotelRestaurant("নিরালা হোটেল", "মোঃ জলিল ফকির", "", ""),
                HotelRestaurant("মোঃ মোহন ফকির হোটেল", "মোঃ মোহন ফকির", "দৌলতদিয়া স্টেশন বাজার, গোয়ালন্দ", ""),
                HotelRestaurant("গোয়ালন্দ ডাকবাংলো", "জেলা পরিষদ, রাজবাড়ী", "গোয়ালন্দ, রাজবাড়ী", "০৬৪২৩-৫৬১০৩"),
                HotelRestaurant("বালিয়াকান্দি ডাক বাংলো", "জেলা পরিষদ, রাজবাড়ী", "বালিয়াকান্দি, রাজবাড়ী", "০৬৪২২-৫৬০০১"),
                HotelRestaurant("হোটেল লতিফ আবাসিক", "খান মোঃ আব্দুল হাই", "টেশনরোড, পাংশা", "০১৭১৬৯৯১১০৬"),
                HotelRestaurant("পাংশা ডাকবাংলো", "জেলা পরিষদ, রাজবাড়ী", "পাংশা, রাজবাড়ী", "০৬৪২৪-৭৫০৩৫"),
                HotelRestaurant("রাজবাড়ী বোডিং", "রতন পোদ্দার", "সাং-কলেজপাড়া, বিনোদপুর, রাজবাড়ী", "০১১৯১৫৭১৭৪৩"),
                HotelRestaurant("মিড টাউন হোটেল", "মোঃ নুরুজ্জামান", "সাং-বেড়াডাঙ্গা, রাজবাড়ী পৌরসভা", "০১১৯০৬৯০৩০১"),
                HotelRestaurant("প্রাইম হোটেল", "মোঃ ছিদ্দিকুর রহমান", "সাং-বেড়াডাঙ্গা, রাজবাড়ী পৌরসভা", "০১৯১৩৯৫৯২২২"),
                HotelRestaurant("গুলশান বোডিং", "আঃ কাদের প্রামানিক", "সাং-সজ্জনকান্দা, রাজবাড়ী পৌরসভা", "০১৯২৩১৯৩১৩৮"),
                HotelRestaurant("হোটেল পার্ক ৩য় তলা আবাসিক", "সাবিত্রি রাণী চক্রবর্ত্তী", "সজ্জনকান্দা, রাজবাড়ী পৌরসভা", "০৬৪১-৬৬১১১")
            )
        )
    }

    var showForm by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var menu by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

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
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(hotelList) { hotel ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("নাম: ${hotel.name}", fontWeight = FontWeight.Bold)
                            if (hotel.menu.isNotBlank()) Text("মালিক: ${hotel.menu}")
                            if (hotel.address.isNotBlank()) Text("ঠিকানা: ${hotel.address}")
                            if (hotel.phone.isNotBlank()) Text("ফোন: ${hotel.phone}")
                        }
                    }
                }
            }

            if (showForm) {
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "নতুন হোটেল/রেস্টুরেন্ট যুক্ত করুন",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("নাম") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = menu,
                    onValueChange = { menu = it },
                    label = { Text("মালিক (ঐচ্ছিক)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("ঠিকানা") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("ফোন নম্বর") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        // Cancel button clears and closes the form
                        showForm = false
                        name = ""
                        menu = ""
                        address = ""
                        phone = ""
                    }) {
                        Text("বাতিল")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        // Save only if name is not blank
                        if (name.isNotBlank()) {
                            hotelList = hotelList + HotelRestaurant(name, menu, address, phone)
                            // Clear form and close it
                            showForm = false
                            name = ""
                            menu = ""
                            address = ""
                            phone = ""
                        }
                    }) {
                        Text("সংরক্ষণ")
                    }
                }
            }
        }
    }
}
