// SearchBar.kt
package com.example.rajbaricity.components
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
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
