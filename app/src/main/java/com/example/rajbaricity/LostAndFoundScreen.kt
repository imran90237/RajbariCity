package com.example.rajbaricity

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun LostAndFoundScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("হারিয়েছি", "পেয়েছি")

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "নতুন ইনফো যোগ করুন")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTabIndex) {
                0 -> LostItemsList()
                1 -> FoundItemsList()
            }

            if (showDialog) {
                AddLostOrFoundDialog(
                    onDismiss = { showDialog = false },
                    onSubmit = { itemName, personName, phone ->
                        // ➕ এখানে API call বা DB insert করতে পারো
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun LostItemsList() {
    // শুধু UI এর জন্য স্যাম্পল টেক্সট
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("হারানো জিনিসের তালিকা")
    }
}

@Composable
fun FoundItemsList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("পাওয়া জিনিসের তালিকা")
    }
}

@Composable
fun AddLostOrFoundDialog(
    onDismiss: () -> Unit,
    onSubmit: (itemName: String, personName: String, phone: String) -> Unit
) {
    var itemName by remember { mutableStateOf(TextFieldValue()) }
    var personName by remember { mutableStateOf(TextFieldValue()) }
    var phone by remember { mutableStateOf(TextFieldValue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("তথ্য যোগ করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("জিনিসের নাম") }
                )
                OutlinedTextField(
                    value = personName,
                    onValueChange = { personName = it },
                    label = { Text("ব্যক্তির নাম") }
                )
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("মোবাইল নাম্বার") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSubmit(itemName.text, personName.text, phone.text)
            }) {
                Text("যোগ করুন")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        }
    )
}
