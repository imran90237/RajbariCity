package com.example.rajbaricity

import LocalMarketScreen
import NearbyMosqueScreen
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.rajbaricity.AddDiseaseSpecialist.AsthmaScreen
import com.example.rajbaricity.AddDiseaseSpecialist.CancerScreen
import com.example.rajbaricity.AddDiseaseSpecialist.ChildScreen
import com.example.rajbaricity.AddDiseaseSpecialist.ChormoScreen
import com.example.rajbaricity.AddDiseaseSpecialist.DentalScreen
import com.example.rajbaricity.AddDiseaseSpecialist.ENTScreen
import com.example.rajbaricity.AddDiseaseSpecialist.EyeScreen
import com.example.rajbaricity.AddDiseaseSpecialist.GynaeScreen
import com.example.rajbaricity.AddDiseaseSpecialist.HomeoScreen
import com.example.rajbaricity.AddDiseaseSpecialist.HormoneScreen
import com.example.rajbaricity.AddDiseaseSpecialist.HridrogScreen
import com.example.rajbaricity.AddDiseaseSpecialist.KidneyScreen
import com.example.rajbaricity.AddDiseaseSpecialist.MedicineScreen
import com.example.rajbaricity.AddDiseaseSpecialist.MonorogScreen
import com.example.rajbaricity.AddDiseaseSpecialist.NutritionScreen
import com.example.rajbaricity.AddDiseaseSpecialist.OrthopedicScreen
import com.example.rajbaricity.AddDiseaseSpecialist.PainScreen
import com.example.rajbaricity.AddDiseaseSpecialist.PhysicalScreen
import com.example.rajbaricity.AddDiseaseSpecialist.PhysiotherapyScreen
import com.example.rajbaricity.AddDiseaseSpecialist.PilesScreen
import com.example.rajbaricity.AddDiseaseSpecialist.PlasticScreen
import com.example.rajbaricity.AddDiseaseSpecialist.SurgeryScreen
import com.example.rajbaricity.AddDiseaseSpecialist.UrologyScreen
import com.example.rajbaricity.AddEducation.CoachingPage
import com.example.rajbaricity.AddEducation.CollegePage
import com.example.rajbaricity.AddEducation.MadrasaPage
import com.example.rajbaricity.AddEducation.SchoolPage
import com.example.rajbaricity.AddEducation.WantToStudyPage
import com.example.rajbaricity.AddEducation.WantToTeachPage

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
                "home" -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        if (route == "education" && selectedSubRoute == null) {
                            EducationScreen { subRoute ->
                                selectedSubRoute = subRoute
                            }
                        } else {
                            when (selectedSubRoute ?: route) {
                                // Education Subpages
                                "school" -> SchoolPage()
                                "college" -> CollegePage()
                                "madrasa" -> MadrasaPage()
                                "coaching" -> CoachingPage()
                                "want_to_study" -> WantToStudyPage()
                                "want_to_teach" -> WantToTeachPage()

                                // Other main routes
                                "doctor" -> DoctorScreen(
                                    onDepartmentClick = { departmentRoute ->
                                        navController.navigate("details/$departmentRoute")
                                    }
                                )
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
                                "local_market" -> LocalMarketScreen()
                                "business_agriculture" -> BusinessAgricultureScreen()
                                "mistry" -> MistryScreen()


                                // ✅ Doctor Departments
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

                "profile" -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "স্বাগতম, ${viewModel.loggedInUserName ?: "ব্যবহারকারী"}!",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                "notification" -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("নোটিফিকেশন আসছে...")
                    }
                }
            }
        }
    }
}
