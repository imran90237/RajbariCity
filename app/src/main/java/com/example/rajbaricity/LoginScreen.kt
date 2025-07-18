package com.example.rajbaricity

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: RajbariViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginFailed by remember { mutableStateOf(false) }

    val loggedInUser by remember { derivedStateOf { viewModel.loggedInUser } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("লগইন পেজ") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Profile Image
            val profilePainter = when {
                loggedInUser?.profileImageUri?.isNotBlank() == true &&
                        loggedInUser?.profileImageUri != "default_avatar" ->
                    rememberAsyncImagePainter(loggedInUser?.profileImageUri)

                else -> painterResource(id = R.drawable.logo)
            }

            Image(
                painter = profilePainter,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "স্বাগতম, ${loggedInUser?.name ?: "ব্যবহারকারী"}!",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = loggedInUser?.emailOrPhone ?: "example@email.com",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("ইউজারনেম বা ফোন নম্বর") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("পাসওয়ার্ড") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            if (loginFailed) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ইউজারনেম বা পাসওয়ার্ড ভুল হয়েছে",
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        val success = viewModel.login(username, password)
                        if (success) {
                            loginFailed = false
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            loginFailed = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("লগইন")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = {
                navController.navigate("register")
            }) {
                Text("নতুন ইউজার? রেজিস্ট্রেশন করুন")
            }
        }
    }
}
