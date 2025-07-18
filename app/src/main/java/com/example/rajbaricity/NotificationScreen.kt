package com.example.rajbaricity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen() {
    // Sample notifications (replace with real data if needed)
    val notifications = listOf(
        "রাজবাড়ী শহরের মেইন রোডে সংস্কার কাজ চলমান।",
        "আজকের আবহাওয়া: হালকা বৃষ্টি সম্ভাবনা।",
        "নতুন বাস রুট সংযুক্ত হয়েছে: রাজবাড়ী → গোয়ালন্দ।",
        "সাধারণ ছুটি ঘোষণা: শুক্রবার ও শনিবার।",
        "আপনার প্রোফাইল সম্পূর্ণ করুন এখনই!"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("নোটিফিকেশন", style = MaterialTheme.typography.titleLarge) }
            )
        }
    ) { padding ->
        if (notifications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "এই মুহূর্তে কোন নোটিফিকেশন নেই",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notifications) { notification ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Text(
                            text = notification,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
        }
    }
}
