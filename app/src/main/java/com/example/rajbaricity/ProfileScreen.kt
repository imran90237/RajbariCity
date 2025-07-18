package com.example.rajbaricity

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: RajbariViewModel
) {
    val userName = viewModel.loggedInUserName ?: "ব্যবহারকারী"
    val userEmail = viewModel.loggedInUserEmail ?: "example@email.com"

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Rajbari City App",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Divider()

                DrawerItem("🏠 হোম") { navController.navigate("home") }
                DrawerItem("🔔 নোটিফিকেশন") { navController.navigate("notification") }
                DrawerItem("📞 যোগাযোগ করুন") { navController.navigate("contact") }
                DrawerItem("👤 প্রোফাইল") { navController.navigate("profile") }

                // Removed toasts — these do nothing now
                DrawerItem("📘 Facebook Page") { /* no-op */ }
                DrawerItem("📱 কল করুন") { /* no-op */ }
                DrawerItem("💬 ম্যাসেজ করুন") { /* no-op */ }
                DrawerItem("📧 ইমেইল করুন") { /* no-op */ }
                DrawerItem("🔗 শেয়ার করুন") { /* no-op */ }
                DrawerItem("🌙 রাত্রি মোড") { /* no-op */ }
                DrawerItem("🔐 গোপনীয়তা নীতি") { /* no-op */ }

                DrawerItem("🚪 লগআউট") {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Rajbari City App") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Authority") },
                                onClick = {
                                    // no-op
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("About App") },
                                onClick = {
                                    // no-op
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("About Developer") },
                                onClick = {
                                    // no-op
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Privacy Policy") },
                                onClick = {
                                    // no-op
                                    showMenu = false
                                }
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.man),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "স্বাগতম, $userName!",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))

                ProfileActionCard("✏ প্রোফাইল সম্পাদনা করুন") {
                    navController.navigate("edit_profile")
                }
                ProfileActionCard("🔔 নোটিফিকেশন") {
                    navController.navigate("notification")
                }
                ProfileActionCard("📞 যোগাযোগ করুন") {
                    navController.navigate("contact")
                }
                ProfileActionCard("📘 Facebook Page") {
                    // no-op
                }
                ProfileActionCard("🚪 লগআউট", isDanger = true) {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ProfileActionCard(text: String, isDanger: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (isDanger) Color(0xFFFFEBEE) else MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(
                text = text,
                color = if (isDanger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
