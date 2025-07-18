package com.example.rajbaricity.Navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rajbaricity.*
import com.example.rajbaricity.EditProfileScreen

@Composable
fun AppNavGraph(navController: NavHostController, viewModel: RajbariViewModel) {
    val startDestination = if (!viewModel.isRegistered) "register"
    else if (viewModel.loggedInUserName == null) "login"
    else "home"

    NavHost(navController = navController, startDestination = startDestination) {

        composable("register") {
            RegistrationScreen(navController, viewModel)
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
            EditProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable("notifications") {
            NotificationScreen()
        }


        composable(
            "details/{route}",
            arguments = listOf(navArgument("route") { type = NavType.StringType })
        ) { backStackEntry ->
            val route = backStackEntry.arguments?.getString("route") ?: "home"
            DetailsScreen(
                route = route,
                navController = navController,
                onHomeClick = {
                    navController.navigate("home") {
                        launchSingleTop = true
                    }
                },
                viewModel = viewModel
            )
        }
    }
}


//package com.example.rajbaricity.Navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import androidx.navigation.NavType
//import com.example.rajbaricity.DetailsScreen
//import com.example.rajbaricity.HomeScreen
//import com.example.rajbaricity.RajbariViewModel
//import com.example.rajbaricity.ui.StationDetailScreen
//
//@Composable
//fun AppNavigation(
//    navController: NavHostController,
//    viewModel: RajbariViewModel
//) {
//    NavHost(navController = navController, startDestination = "home") {
//
//        // HomeScreen
//        composable("home") {
//            HomeScreen(
//                sections = viewModel.sections,
//                onSectionClick = { route ->
//                    navController.navigate("details/$route")
//                }
//            )
//        }
//
//        // DetailsScreen
//        composable(
//            "details/{route}",
//            arguments = listOf(navArgument("route") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val route = backStackEntry.arguments?.getString("route") ?: "home"
//            DetailsScreen(
//                route = route,
//                navController = navController,
//                onHomeClick = {
//                    navController.navigate("home") {
//                        launchSingleTop = true
//                    }
//                },
//                viewModel = viewModel
//            )
//        }
//
//        // ✅ Station Detail Screen route (এই অংশটাই আগের কোডে বাইরে ছিল)
//        composable(
//            route = "station_detail/{stationName}",
//            arguments = listOf(navArgument("stationName") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val stationName = backStackEntry.arguments?.getString("stationName") ?: ""
//            StationDetailScreen(
//                stationName = stationName,
//                navController = navController
//            )
//        }
//    }
//}




//package com.example.rajbaricity.Navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.navArgument
//import androidx.navigation.NavType
//import com.example.rajbaricity.DetailsScreen
//import com.example.rajbaricity.HomeScreen
//import com.example.rajbaricity.RajbariViewModel
//
//@Composable
//fun AppNavigation(
//    navController: NavHostController,
//    viewModel: RajbariViewModel
//) {
//    NavHost(navController = navController, startDestination = "details/home") {
//
//        composable(
//            route = "details/{route}",
//            arguments = listOf(navArgument("route") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val route = backStackEntry.arguments?.getString("route") ?: "home"
//            DetailsScreen(
//                route = route,
//                navController = navController,
//                onHomeClick = {
//                    navController.navigate("details/home") {
//                        launchSingleTop = true
//                    }
//                },
//                viewModel = viewModel
//            )
//        }
//    }
//}
