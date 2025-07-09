package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TouristPlacesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "🌍 রাজবাড়ীর দর্শনীয় স্থানসমূহ",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TouristPlaceCard(
            title = "Godar Bazar",
            location = "রাজবাড়ী শহর সংলগ্ন",
            highlights = listOf("নদীর দৃশ্য", "ঘাট", "সান্ধ্যকালীন সৌন্দর্য"),
            mapUrl = "https://maps.google.com/?q=Godar+Bazar",
            imageResId = R.drawable.godar_bazar
        )

        Spacer(modifier = Modifier.height(16.dp))

        TouristPlaceCard(
            title = "রাজবাড়ী শিশু পার্ক",
            location = "রাজবাড়ী শহর",
            highlights = listOf("বাচ্চাদের খেলার জায়গা", "পরিবারের জন্য সুন্দর পরিবেশ"),
            mapUrl = "https://maps.google.com/?q=Rajbari+Shishu+Park",
            imageResId = R.drawable.shishu_park
        )

        Spacer(modifier = Modifier.height(16.dp))

        TouristPlaceCard(
            title = "মীর মোশারফ হোসেন স্মৃতি কমপ্লেক্স",
            location = "পদমদী, রাজবাড়ী",
            highlights = listOf("ঐতিহাসিক স্থান", "স্মৃতিচিহ্ন", "সাংস্কৃতিক কেন্দ্র"),
            mapUrl = "https://maps.google.com/?q=Mir+Mosharraf+Hossain+Memorial+Complex",
            imageResId = R.drawable.mir_mosharrof
        )

//        Spacer(modifier = Modifier.height(16.dp))
//
//        TouristPlaceCard(
//            title = "Goduli Park",
//            location = "রাজবাড়ী শহর সংলগ্ন",
//            highlights = listOf("সবুজ পার্ক", "পারিবারিক বিনোদন"),
//            mapUrl = "https://maps.google.com/?q=Goduli+Park",
//            imageResId = R.drawable.goduli_park
//        )
    }
}


@Composable
fun TouristPlaceCard(
    title: String,
    location: String,
    highlights: List<String>,
    mapUrl: String,
    imageResId: Int
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // 🌄 Image at the top of the card
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = "অবস্থান: $location", fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))

                highlights.forEach {
                    Text("• $it", fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl))
                    context.startActivity(intent)
                }) {
                    Text(text = "🗺 Google Map এ দেখুন", color = Color(0xFF1E88E5))
                }
            }
        }
    }
}
