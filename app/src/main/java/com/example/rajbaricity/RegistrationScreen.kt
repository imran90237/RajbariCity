//package com.example.rajbaricity
//
//import android.net.Uri
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import coil.compose.rememberAsyncImagePainter
//import com.example.rajbaricity.model.User
//import com.example.rajbaricity.ui.RajbariViewModel
//import kotlinx.coroutines.launch
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RegistrationScreen(navController: NavController, viewModel: RajbariViewModel) {
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()
//
//    var username by remember { mutableStateOf("") }
//    var emailOrPhone by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }
//    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        profileImageUri = uri
//    }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp)
//                .padding(padding),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("রেজিস্ট্রেশন করুন", style = MaterialTheme.typography.headlineSmall)
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Image(
//                painter = if (profileImageUri != null)
//                    rememberAsyncImagePainter(profileImageUri)
//                else
//                    painterResource(id = R.drawable.man),
//                contentDescription = "Profile Image",
//                modifier = Modifier
//                    .size(100.dp)
//                    .clip(CircleShape)
//                    .border(2.dp, Color.Gray, CircleShape)
//                    .clickable {
//                        launcher.launch("image/*")
//                    }
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            OutlinedTextField(
//                value = username,
//                onValueChange = { username = it },
//                label = { Text("ইউজারনেম") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = emailOrPhone,
//                onValueChange = { emailOrPhone = it },
//                label = { Text("ইমেইল / মোবাইল নম্বর") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            OutlinedTextField(
//                value = password,
//                onValueChange = { password = it },
//                label = { Text("পাসওয়ার্ড") },
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = {
//                    if (username.isBlank() || emailOrPhone.isBlank() || password.isBlank()) {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("সব ফিল্ড পূরণ করুন")
//                        }
//                    } else {
//                        val imageString = profileImageUri?.toString() ?: "man"
//                        val email = if (emailOrPhone.contains("@")) emailOrPhone else ""
//                        val phone = if (!emailOrPhone.contains("@")) emailOrPhone else ""
//
//                        val user = User(
//                            username = username,
//                            email = email,
//                            phone = phone,
//                            password = password,
//                            profileImageUri = imageString
//                        )
//
//                        viewModel.registerUserOnline(user) { success, message ->
//                            coroutineScope.launch {
//                                if (success) {
//                                    snackbarHostState.showSnackbar("রেজিস্ট্রেশন সফল হয়েছে")
//                                    navController.navigate("login") {
//                                        popUpTo("register") { inclusive = true }
//                                    }
//                                } else {
//                                    snackbarHostState.showSnackbar(message ?: "রেজিস্ট্রেশন ব্যর্থ হয়েছে")
//                                }
//                            }
//                        }
//                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("রেজিস্টার")
//            }
//        }
//    }
//}







package com.example.rajbaricity

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.model.User
import com.example.rajbaricity.ui.RajbariViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController, viewModel: RajbariViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var emailOrPhone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("রেজিস্ট্রেশন করুন", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = if (profileImageUri != null)
                    rememberAsyncImagePainter(profileImageUri)
                else
                    painterResource(id = R.drawable.man), // your default image resource
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
                    .clickable {
                        launcher.launch("image/*")
                    }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("ইউজারনেম") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = emailOrPhone,
                onValueChange = { emailOrPhone = it },
                label = { Text("ইমেইল / মোবাইল নম্বর") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("পাসওয়ার্ড") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Basic input validation
                    if (username.isBlank() || emailOrPhone.isBlank() || password.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("সব ফিল্ড পূরণ করুন")
                        }
                        return@Button
                    }

                    // Determine if input is email or phone
                    val email = if (emailOrPhone.contains("@")) emailOrPhone else ""
                    val phone = if (!emailOrPhone.contains("@")) emailOrPhone else ""

                    // Create user object
                    val user = User(
                        username = username.trim(),
                        email = email.trim(),
                        phone = phone.trim(),
                        password = password,
                        profileImageUri = profileImageUri?.toString() ?: "man"
                    )

                    // Call ViewModel to register online
                    viewModel.registerUserOnline(user) { success, message ->
                        coroutineScope.launch {
                            if (success) {
                                snackbarHostState.showSnackbar("রেজিস্ট্রেশন সফল হয়েছে")
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                snackbarHostState.showSnackbar(message ?: "রেজিস্ট্রেশন ব্যর্থ হয়েছে")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("রেজিস্টার")
            }
        }
    }
}
