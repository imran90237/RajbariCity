package com.example.rajbaricity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rajbaricity.AddDiseaseSpecialist.*
import com.example.rajbaricity.AddEducation.*
import com.example.rajbaricity.ui.RajbariViewModel

@Composable
fun DetailsScreen(
    route: String,
    onHomeClick: () -> Unit,
    navController: NavController,
    viewModel: RajbariViewModel
) {
    var selectedTab by remember { mutableStateOf("home") }
    var selectedSubRoute by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == "home",
                    onClick = {
                        selectedTab = "home"
                        selectedSubRoute = null
                        onHomeClick()
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("হোম") }
                )
                NavigationBarItem(
                    selected = selectedTab == "notification",
                    onClick = {
                        selectedTab = "notification"
                        selectedSubRoute = null
                    },
                    icon = { Icon(Icons.Default.Notifications, contentDescription = "Notification") },
                    label = { Text("নোটিফিকেশন") }
                )
                NavigationBarItem(
                    selected = selectedTab == "profile",
                    onClick = {
                        selectedTab = "profile"
                        selectedSubRoute = null
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("প্রোফাইল") }
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                "home" -> HomeContent(
                    route = route,
                    selectedSubRoute = selectedSubRoute,
                    onSubRouteSelected = { selectedSubRoute = it },
                    navController = navController
                )
                "profile" -> ProfileScreen(navController = navController, viewModel = viewModel)
                "notification" -> NotificationScreen()
            }
        }
    }
}


@Composable
fun HomeContent(
    route: String,
    selectedSubRoute: String?,
    onSubRouteSelected: (String) -> Unit,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (route == "education" && selectedSubRoute == null) {
            EducationScreen(onSubRouteSelected)
        } else {
            when (selectedSubRoute ?: route) {
                "school" -> SchoolPage()
                "college" -> CollegePage()
                "madrasa" -> MadrasaPage()
                "coaching" -> CoachingPage()
                "want_to_study" -> WantToStudyPage()
                "want_to_teach" -> WantToTeachPage()

                "doctor" -> DoctorScreen(onDepartmentClick =
                    { departmentRoute -> navController.navigate("details/$departmentRoute") })
                "hospital" -> HospitalScreen(navController)
                "blood" -> BloodScreen()
                "bus_schedule" -> BusScheduleScreen()
                "train_schedule" -> TrainScheduleScreen(navController)
                "emergency" -> EmergencyNumberScreen()
                "tourist_places" -> TouristPlacesScreen()
                "websites" -> WebsiteListScreen()
                "hotels_restaurants" -> HotelRestaurantScreen()
                "mosque_nearby" -> NearbyMosqueScreen()
                "car_rent" -> CarRentScreen()
                "courier" -> CourierServiceScreen()
                "lost_found" -> LostAndFoundScreen()
                "jobs_training" -> JobsTrainingScreen()
                "Shopping" -> ShoppingScreen()
                "Nursery" -> NurseryScreen()
                "mistry" -> MistryScreen()

                // Disease specialist screens
                "monorog" -> MonorogScreen()
                "hridrog" -> HridrogScreen()
                "piles" -> PilesScreen()
                "dental" -> DentalScreen()
                "chormo" -> ChormoScreen()
                "hormone" -> HormoneScreen()
                "ent" -> ENTScreen()
                "eye" -> EyeScreen()
                "urology" -> UrologyScreen()
                "gynae" -> GynaeScreen()
                "homeo" -> HomeoScreen()
                "surgery" -> SurgeryScreen()
                "medicine" -> MedicineScreen()
                "kidney" -> KidneyScreen()
                "nutrition" -> NutritionScreen()
                "cancer" -> CancerScreen()
                "orthopedic" -> OrthopedicScreen()
                "pain" -> PainScreen()
                "child" -> ChildScreen()
                "physical" -> PhysicalScreen()
                "physiotherapy" -> PhysiotherapyScreen()
                "plastic" -> PlasticScreen()
                "asthma" -> AsthmaScreen()

                else -> DefaultScreen()
            }
        }
    }
}