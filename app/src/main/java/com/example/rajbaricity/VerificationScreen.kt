package com.example.rajbaricity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rajbaricity.ui.RajbariViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: RajbariViewModel,
    email: String
) {
    var code by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verify Email") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("A verification code has been sent to $email. Please enter it below.")
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Verification Code") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        isLoading = true
                        errorMessage = null
                        viewModel.verifyAndRegister(email, code) { success, message ->
                            isLoading = false
                            if (success) {
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                errorMessage = message ?: "Verification failed"
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Verify and Register")
                }
            }
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
