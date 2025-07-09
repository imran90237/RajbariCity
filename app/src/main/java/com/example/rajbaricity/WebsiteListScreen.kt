package com.example.rajbaricity

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WebsiteListScreen(onHomeClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "🌐 জনপ্রিয় ওয়েবসাইটসমূহ",
            style = MaterialTheme.typography.titleLarge
        )

        WebsiteCard(title = "রাজবাড়ী জেলা অফিসিয়াল  (ওয়েবসাইটে ভিজিট করুন) ", url = "https://www.rajbari.gov.bd")
        WebsiteCard(title = "রাজবাড়ী সদর উপজেলা  (ওয়েবসাইটে ভিজিট করুন", url = "https://sadar.rajbari.gov.bd")
        WebsiteCard(title = "গোয়ালন্দ উপজেলা  (ওয়েবসাইটে ভিজিট করুন", url = "https://goalandup.rajbari.gov.bd")
        WebsiteCard(title = "পাংশা উপজেলা  (ওয়েবসাইটে ভিজিট করুন", url = "https://pangsaup.rajbari.gov.bd")
        WebsiteCard(title = "বালিয়াকান্দি উপজেলা  (ওয়েবসাইটে ভিজিট করুন", url = "https://baliakandi.rajbari.gov.bd")
        WebsiteCard(title = "কালুখালী উপজেলা  (ওয়েবসাইটে ভিজিট করুন", url = "https://kalkukhali.rajbari.gov.bd")
        WebsiteCard(title = "বাংলাদেশ পর্যটন কর্পোরেশন  (ওয়েবসাইটে ভিজিট করুন", url = "https://www.parjatan.gov.bd")
        WebsiteCard(title = "জাতীয় বিশ্ববিদ্যালয়  (ওয়েবসাইটে ভিজিট করুন", url = "https://www.nu.ac.bd")
        WebsiteCard(title = "জাতীয় পরিচয় সেবা  (ওয়েবসাইটে ভিজিট করুন", url = "https://services.nidw.gov.bd")
        WebsiteCard(title = "ভূমি রেকর্ড ও মানচিত্র  (ওয়েবসাইটে ভিজিট করুন", url = "https://dlrms.land.gov.bd")
        WebsiteCard(title = "বিআরটিএ সেবা বাতায়ন  (ওয়েবসাইটে ভিজিট করুন", url = "https://bsp.brta.gov.bd")
        WebsiteCard(title = "ই-পাসপোর্ট  (ওয়েবসাইটে ভিজিট করুন", url = "https://www.epassport.gov.bd")
        WebsiteCard(title = "স্থানীয় নিউজ পোর্টাল  (ওয়েবসাইটে ভিজিট করুন", url = "https://example-news-site.com") // আপনি চাইলে এখানে আসল নিউজ লিংক বসাতে পারেন

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun WebsiteCard(title: String, url: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color(0xFF2E7D32)
            )
        }
    }
}
