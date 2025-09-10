package com.example.rajbaricity.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rajbaricity.*
import com.example.rajbaricity.ui.RajbariViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: RajbariViewModel) {
    val startDestination = if (viewModel.loggedInUserName == null) "login"
    else "home"

    NavHost(navController = navController, startDestination = startDestination) {

        composable("register") {
            RegistrationScreen(
                navController = navController,
                viewModel = viewModel,
                onRegisterSuccess = { username, email, password ->
                    val encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString())
                    val encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString())
                    val encodedPassword = URLEncoder.encode(password, StandardCharsets.UTF_8.toString())
                    navController.navigate("verification/$encodedUsername/$encodedEmail/$encodedPassword")
                }
            )
        }

        composable(
            route = "verification/{username}/{email}/{password}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: ""
            val email = backStackEntry.arguments?.getString("email")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: ""
            val password = backStackEntry.arguments?.getString("password")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: ""
            VerificationScreen(
                navController = navController,
                viewModel = viewModel,
                username = username,
                email = email,
                password = password
            )
        }

        composable("login") {
            LoginScreen(navController, viewModel)
        }

        composable("home") {
            HomeScreen(
                sections = viewModel.sections,
                onSectionClick = { route ->
                    navController.navigate("details/$route")
                }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(navController, viewModel)
        }

        composable("notifications") {
            NotificationScreen()
        }

        // 🔥 Dynamic section details page
        composable(
            route = "details/{route}",
            arguments = listOf(navArgument("route") { type = NavType.StringType })
        ) { backStackEntry ->
            val route = backStackEntry.arguments?.getString("route") ?: "unknown"
            DetailsScreen(
                route = route,
                navController = navController,
                onHomeClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                viewModel = viewModel
            )
        }
    }
}