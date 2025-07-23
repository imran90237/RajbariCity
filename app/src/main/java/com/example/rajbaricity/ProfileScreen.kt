package com.example.rajbaricity

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
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
import coil.compose.rememberAsyncImagePainter
import com.example.rajbaricity.ui.RajbariViewModel
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: RajbariViewModel) {
    val userName = viewModel.loggedInUserName ?: "ব্যবহারকারী"
    val userEmail = viewModel.loggedInUserEmail ?: "example@email.com"
    val userImageUri: Uri? = viewModel.loggedInUserImageUri
    val userPhone = viewModel.loggedInUserPhone ?: "মোবাইল নম্বর নেই"

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }
    var isDarkMode by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    // Header section with dark mode top-right
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Dark Mode Button (top right)
                        IconButton(
                            onClick = { isDarkMode = !isDarkMode },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Brightness4,
                                contentDescription = "Dark Mode Toggle",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // User Info Section
                    UserHeader(userName, userPhone, userImageUri)

                    Divider(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        thickness = 2.dp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // Drawer Items (left aligned)
                    DrawerItem("🏠 হোম") {
                        scope.launch { drawerState.close() }
                        navController.navigate("home")
                    }
                    DrawerItem("🔔 নোটিফিকেশন") {
                        scope.launch { drawerState.close() }
                        navController.navigate("notification")
                    }
                    DrawerItem("👤 প্রোফাইল") {
                        scope.launch { drawerState.close() }
                        navController.navigate("profile")
                    }
                    DrawerItem("📞 যোগাযোগ করুন") {
                        scope.launch { drawerState.close() }
                        navController.navigate("contact")
                    }
                    DrawerItem("🚪 লগআউট") {
                        scope.launch { drawerState.close() }
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
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
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
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
                            DropdownMenuItem(text = { Text("Authority") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("About App") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("About Developer") }, onClick = { showMenu = false })
                            DropdownMenuItem(text = { Text("Privacy Policy") }, onClick = { showMenu = false })
                        }
                    }
                )
            }
        ) { innerPadding ->
            ProfileContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                userName = userName,
                userEmail = userEmail,
                onEditProfile = { navController.navigate("edit_profile") },
                onNotifications = { navController.navigate("notification") },
                onContact = { navController.navigate("contact") },
                onLogout = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
private fun UserHeader(
    userName: String,
    userPhone: String,
    userImageUri: Uri?
)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(userImageUri),
                contentDescription = "User Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Text(
                    text = userName.take(2).uppercase(Locale.getDefault()),
                    color = Color.White,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = userName,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = userPhone,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DrawerItem(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentPadding = PaddingValues(0.dp) // removes default padding
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    userName: String,
    userEmail: String,
    onEditProfile: () -> Unit,
    onNotifications: () -> Unit,
    onContact: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier,
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

        ProfileActionCard("✏ প্রোফাইল সম্পাদনা করুন", onClick = onEditProfile)
        ProfileActionCard("🔔 নোটিফিকেশন", onClick = onNotifications)
        ProfileActionCard("📞 যোগাযোগ করুন", onClick = onContact)
        ProfileActionCard("📘 Facebook Page", onClick = {}) // Implement later
        ProfileActionCard("🚪 লগআউট", isDanger = true, onClick = onLogout)
    }
}

@Composable
private fun ProfileActionCard(text: String, isDanger: Boolean = false, onClick: () -> Unit) {
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
