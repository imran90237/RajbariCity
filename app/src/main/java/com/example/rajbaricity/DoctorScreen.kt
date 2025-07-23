package com.example.rajbaricity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DoctorScreen(
    onDepartmentClick: (String) -> Unit,
    onHomeClick: () -> Unit = {}
) {
    val specialties = listOf(
        "medicine" to "💊 মেডিসিন",
        "hormone" to "🧬 হরমোন",
        "nutrition" to "🥦 পুষ্টি",
        "pain" to "🌪️ ব্যথা",
        "asthma" to "🌬️ অ্যাজমা ও বক্ষ",
        "cancer" to "🎗️ ক্যান্সার",
        "monorog" to "🧠 মনোরোগ",
        "hridrog" to "❤️ হৃদরোগ",
        "kidney" to "🚰 কিডনি",
        "urology" to "🧫 ইউরোলজি",
        "child" to "🛝 শিশুরোগ",
        "gynae" to "🌸 গাইনি",
        "dental" to "🦷 ডেন্টাল",
        "eye" to "👁️ চক্ষু",
        "ent" to "👃 নাক কান গলা",
        "surgery" to "🔪 সার্জারি",
        "orthopedic" to "🦴 অর্থোপেডিক",
        "plastic" to "🧊 প্লাস্টিক",
        "piles" to "🔥 পাইলস",
        "chormo" to "🧴 চর্ম ও যৌন",
        "physiotherapy" to "👐 ফিজিওথেরাপি",
        "physical" to "🌀 ফিজিক্যাল",
        "homeo" to "🌿 হোমিও"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "🩺 ডাক্তার বিভাগসমূহ",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 34.sp),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize(), // পূর্ণ স্ক্রিন নেয় এবং স্ক্রল করে
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(specialties) { (key, label) ->
                Card(
                    onClick = { onDepartmentClick(key) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            softWrap = true,
                            maxLines = 3,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    } // <-- এই হচ্ছে Column এর বন্ধনী
} // <-- এই হচ্ছে DoctorScreen ফাংশনের শেষ বন্ধনী
