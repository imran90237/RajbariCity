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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.model.Shopping
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun ShoppingScreen(viewModel: RajbariViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.getShoppings()
    }

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("নতুন পণ্য", "পুরাতন পণ্য")
    var showForm by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val shoppings by viewModel.shoppings.collectAsState()

    val filteredList = shoppings.filter {
        val matchTab = if (selectedTab == 0) it.isNew else !it.isNew
        val matchSearch = it.title.contains(searchQuery.text, ignoreCase = true) ||
                it.details.contains(searchQuery.text, ignoreCase = true)
        matchTab && matchSearch
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showForm = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            Text(
                "🛍️ শপিং",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

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

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredList) { shopping ->
                    ShoppingCard(shopping)
                }
            }
        }

        if (showForm) {
            AddShoppingDialog(
                onDismiss = { showForm = false },
                onSubmit = { newShopping ->
                    viewModel.addShopping(newShopping)
                    showForm = false
                }
            )
        }
    }
}

@Composable
fun ShoppingCard(shopping: Shopping) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = shopping.photoUrl.ifBlank { R.drawable.logo },
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.logo)
            )
            Column {
                Text(shopping.title, fontWeight = FontWeight.Bold)
                Text(shopping.details)
                Text("📍 ${shopping.address}")
                Text("💰 ${shopping.price}")
                Text("📞 ${shopping.mobile}")
            }
        }
    }
}

@Composable
fun AddShoppingDialog(
    onDismiss: () -> Unit,
    onSubmit: (Shopping) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var mobile by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isNew by remember { mutableStateOf(true) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        selectedImageUri = uri
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val shopping = Shopping(
                    title = title,
                    details = details,
                    address = address,
                    price = price,
                    mobile = mobile,
                    photoUrl = selectedImageUri?.toString() ?: "",
                    isNew = isNew
                )
                onSubmit(shopping)
            }) {
                Text("সাবমিট")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("বাতিল")
            }
        },
        title = { Text("পণ্য যোগ করুন") },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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

                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("শিরোনাম") })
                OutlinedTextField(value = details, onValueChange = { details = it }, label = { Text("বিস্তারিত") })
                OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("ঠিকানা") })
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("মূল্য") })
                OutlinedTextField(value = mobile, onValueChange = { mobile = it }, label = { Text("মোবাইল নাম্বার") })
            }
        }
    )
}