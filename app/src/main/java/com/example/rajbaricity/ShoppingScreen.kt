
package com.example.rajbaricity

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

// ✅ Product Model
data class Product(
    val title: String,
    val details: String,
    val address: String,
    val price: String,
    val mobile: String,
    val imageUri: Uri? = null,
    val isNew: Boolean = true
)

@Composable
fun ShoppingScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("নতুন পণ্য", "পুরাতন পণ্য")
    var showForm by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    var productList by remember {
        mutableStateOf(
            listOf(
                Product("নতুন জামা", "কটন ফ্যাব্রিক", "সিলেট শহর", "৫০০ টাকা", "017XXXXXXXX", isNew = true),
                Product("পুরাতন ব্যাগ", "ব্যবহৃত কিন্তু ভালো", "উপশহর", "২০০ টাকা", "018XXXXXXXX", isNew = false)
            )
        )
    }

    val filteredList = productList.filter {
        val matchTab = if (selectedTab == 0) it.isNew else !it.isNew
        val matchSearch = it.title.contains(searchQuery.text, ignoreCase = true) ||
                it.details.contains(searchQuery.text, ignoreCase = true)
        matchTab && matchSearch
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("🛍️ শপিং", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))

            // ✅ Tab Row
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(text) }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // ✅ Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("সার্চ করুন...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            // ✅ Product List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(filteredList) { product ->
                    ProductCard(product)
                }
            }

            // ✅ Add Product Dialog
            if (showForm) {
                AddProductDialog(
                    onDismiss = { showForm = false },
                    onSubmit = { newProduct ->
                        productList = productList + newProduct
                        showForm = false
                    }
                )
            }
        }

        // ✅ Floating Action Button
        FloatingActionButton(
            onClick = { showForm = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Product")
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = product.imageUri?.let { rememberAsyncImagePainter(it) }
                    ?: painterResource(id = R.drawable.logo), // 🔄 Default logo
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(product.title, fontWeight = FontWeight.Bold)
                Text(product.details)
                Text("📍 ${product.address}")
                Text("💰 ${product.price}")
                Text("📞 ${product.mobile}")
            }
        }
    }
}

@Composable
fun AddProductDialog(
    onDismiss: () -> Unit,
    onSubmit: (Product) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isNew by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val product = Product(
                    title = title,
                    details = details,
                    address = address,
                    price = price,
                    mobile = mobile,
                    imageUri = selectedImageUri,
                    isNew = isNew
                )
                onSubmit(product)
            }) {
                Text("সাবমিট")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        },
        title = { Text(" পণ্য যোগ করুন") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // ✅ Product Type Selection
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = isNew,
                        onClick = { isNew = true }
                    )
                    Text("নতুন", modifier = Modifier.padding(end = 16.dp))

                    RadioButton(
                        selected = !isNew,
                        onClick = { isNew = false }
                    )
                    Text("পুরাতন")
                }

                // ✅ Image Picker Placeholder
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                // TODO: Add image picker logic
                            },
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                Icons.Default.AddPhotoAlternate,
                                contentDescription = "Select Photo",
                                modifier = Modifier.size(32.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Image(
                        painter = selectedImageUri?.let { rememberAsyncImagePainter(it) }
                            ?: painterResource(id = R.drawable.logo),
                        contentDescription = "Preview",
                        modifier = Modifier
                            .size(64.dp)
                            .aspectRatio(1f)
                            .padding(4.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                // ✅ Input Fields
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("শিরোনাম") })
                OutlinedTextField(value = details, onValueChange = { details = it }, label = { Text("বিস্তারিত") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("মূল্য") })
                OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নাম্বার") })
            }
        }
    )
}
