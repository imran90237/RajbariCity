package com.example.rajbaricity.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rajbaricity.*
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: RajbariViewModel) {
    val startDestination = if (!viewModel.isRegistered) "register"
    else if (viewModel.loggedInUserName == null) "login"
    else "home"

    NavHost(navController = navController, startDestination = startDestination) {

        composable("register") {
            RegistrationScreen(
                navController = navController,
                viewModel = viewModel,
                onRegisterSuccess = { email ->
                    navController.navigate("verification/$email")
                }
            )
        }

        composable(
            route = "verification/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerificationScreen(
                navController = navController,
                viewModel = viewModel,
                email = email
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
