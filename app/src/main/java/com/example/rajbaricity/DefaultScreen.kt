package com.example.rajbaricity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun DefaultScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("পেজ খুঁজে পাওয়া যায়নি", fontSize = 20.sp)
        Text("অনুগ্রহ করে হোমে ফিরে যান।", fontSize = 16.sp)
    }
}
