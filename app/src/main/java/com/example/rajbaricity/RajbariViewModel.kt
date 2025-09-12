package com.example.rajbaricity.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rajbaricity.model.*
import com.example.rajbaricity.network.RetrofitClient
import com.example.rajbaricity.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RajbariViewModel : ViewModel() {

    private val _donors = MutableStateFlow<List<BloodDonor>>(emptyList())
    val donors: StateFlow<List<BloodDonor>> = _donors

    private val _requests = MutableStateFlow<List<BloodRequest>>(emptyList())
    val requests: StateFlow<List<BloodRequest>> = _requests

    private val _busCounters = MutableStateFlow<List<BusCounter>>(emptyList())
    val busCounters: StateFlow<List<BusCounter>> = _busCounters

    private val _busTimes = MutableStateFlow<List<Bustime>>(emptyList())
    val busTimes: StateFlow<List<Bustime>> = _busTimes

    private val _cars = MutableStateFlow<List<CarInfo>>(emptyList())
    val cars: StateFlow<List<CarInfo>> = _cars

    private val _coachings = MutableStateFlow<List<CoachingInfo>>(emptyList())
    val coachings: StateFlow<List<CoachingInfo>> = _coachings

    private val _colleges = MutableStateFlow<List<CollegeInfo>>(emptyList())
    val colleges: StateFlow<List<CollegeInfo>> = _colleges

    private val _qawmiMadrasas = MutableStateFlow<List<MadrasaInfo>>(emptyList())
    val qawmiMadrasas: StateFlow<List<MadrasaInfo>> = _qawmiMadrasas

    private val _aliaMadrasas = MutableStateFlow<List<MadrasaInfo>>(emptyList())
    val aliaMadrasas: StateFlow<List<MadrasaInfo>> = _aliaMadrasas

    private val _schools = MutableStateFlow<List<SchoolInfo>>(emptyList())
    val schools: StateFlow<List<SchoolInfo>> = _schools

    private val _students = MutableStateFlow<List<Student>>(emptyList())
    val students: StateFlow<List<Student>> = _students

    private val users = mutableStateListOf<User>()

    var loggedInUser by mutableStateOf<User?>(null)
        private set

    val isRegistered: Boolean
        get() = users.isNotEmpty()

    val loggedInUserName: String?
        get() = loggedInUser?.username

    val loggedInUserEmail: String?
        get() = loggedInUser?.email

    val loggedInUserImage: String?
        get() = loggedInUser?.profileImageUrl

    val loggedInUserImageUri: Uri?
        get() = loggedInUser?.profileImageUrl?.let { Uri.parse(it) }

    init {
        getDonors()
        getRequests()
        getBusCounters()
        getBusTimes()
        getCars()
        getCoachings()
        getColleges()
        getMadrasas()
        getSchools()
        getStudents()
    }

    fun getStudents() {
        viewModelScope.launch {
            try {
                _students.value = RetrofitClient.studentApiService.getAllStudents()
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error fetching students", e)
            }
        }
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            try {
                RetrofitClient.studentApiService.createStudent(student)
                getStudents() // Refresh the list
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error adding student", e)
            }
        }
    }

    fun likeStudent(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitClient.studentApiService.likeStudent(id)
                getStudents() // Refresh the list to show updated like count
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error liking student", e)
            }
        }
    }

    fun getCoachings() {
        viewModelScope.launch {
            try {
                _coachings.value = RetrofitClient.coachingApiService.getAll()
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error fetching coachings", e)
            }
        }
    }

    fun addCoaching(coachingInfo: CoachingInfo) {
        viewModelScope.launch {
            try {
                RetrofitClient.coachingApiService.create(coachingInfo)
                getCoachings() // Refresh the list
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error adding coaching", e)
            }
        }
    }

    fun getColleges() {
        viewModelScope.launch {
            try {
                _colleges.value = RetrofitClient.collegeApiService.getAll()
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error fetching colleges", e)
            }
        }
    }

    fun addCollege(collegeInfo: CollegeInfo) {
        viewModelScope.launch {
            try {
                RetrofitClient.collegeApiService.create(collegeInfo)
                getColleges() // Refresh the list
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error adding college", e)
            }
        }
    }

    fun getMadrasas() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.madrasaApiService.getAll()
                if (response.isSuccessful) {
                    val allMadrasas = response.body() ?: emptyList()
                    Log.d("RajbariViewModel", "Madrasas fetched successfully: ${allMadrasas.size} madrasas")
                    allMadrasas.forEach { madrasa ->
                        Log.d("RajbariViewModel", "Madrasa: ${madrasa.name}, Type: ${madrasa.type}")
                    }
                    _qawmiMadrasas.value = allMadrasas.filter { it.type?.trim().equals("Qawmi", ignoreCase = true) }
                    _aliaMadrasas.value = allMadrasas.filter { it.type?.trim().equals("Alia", ignoreCase = true) }
                } else {
                    Log.e("RajbariViewModel", "Failed to fetch madrasas. Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error fetching madrasas", e)
            }
        }
    }

    fun addMadrasa(madrasaInfo: MadrasaInfo) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.madrasaApiService.create(madrasaInfo)
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Madrasa added successfully: ${response.body()}")
                    getMadrasas() // Refresh both lists
                } else {
                    Log.e("RajbariViewModel", "Failed to add madrasa. Code: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error adding madrasa", e)
            }
        }
    }

    fun getSchools() {
        viewModelScope.launch {
            try {
                _schools.value = RetrofitClient.schoolApiService.getAll()
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error fetching schools", e)
            }
        }
    }

    fun addSchool(schoolInfo: SchoolInfo) {
        viewModelScope.launch {
            try {
                RetrofitClient.schoolApiService.create(schoolInfo)
                getSchools() // Refresh the list
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Error adding school", e)
            }
        }
    }

    fun getCars() {
        Log.d("RajbariViewModel", "Fetching cars...")
        RetrofitClient.carApiService.getCars().enqueue(object : Callback<List<CarInfo>> {
            override fun onResponse(call: Call<List<CarInfo>>, response: Response<List<CarInfo>>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Cars fetched successfully: ${response.body()?.size} cars")
                    _cars.value = response.body() ?: emptyList()
                } else {
                    Log.e("RajbariViewModel", "Failed to fetch cars. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<CarInfo>>, t: Throwable) {
                Log.e("RajbariViewModel", "Error fetching cars", t)
            }
        })
    }

    fun addCar(carInfo: CarInfo) {
        Log.d("RajbariViewModel", "Adding car: $carInfo")
        RetrofitClient.carApiService.addCar(carInfo).enqueue(object : Callback<CarInfo> {
            override fun onResponse(call: Call<CarInfo>, response: Response<CarInfo>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Car added successfully: ${response.body()}")
                    getCars() // Refresh the list
                } else {
                    Log.e("RajbariViewModel", "Failed to add car. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CarInfo>, t: Throwable) {
                Log.e("RajbariViewModel", "Error adding car", t)
            }
        })
    }



    fun getBusCounters() {
        Log.d("RajbariViewModel", "Fetching bus counters...")
        RetrofitClient.busCounterApiService.getCounters().enqueue(object : Callback<List<BusCounter>> {
            override fun onResponse(call: Call<List<BusCounter>>, response: Response<List<BusCounter>>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Bus counters fetched successfully: ${response.body()?.size} counters")
                    _busCounters.value = response.body() ?: emptyList()
                } else {
                    Log.e("RajbariViewModel", "Failed to fetch bus counters. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<BusCounter>>, t: Throwable) {
                Log.e("RajbariViewModel", "Error fetching bus counters", t)
            }
        })
    }

    fun getBusTimes() {
        Log.d("RajbariViewModel", "Fetching bus times...")
        RetrofitClient.bustimeApiService.getBusTimes().enqueue(object : Callback<List<Bustime>> {
            override fun onResponse(call: Call<List<Bustime>>, response: Response<List<Bustime>>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Bus times fetched successfully: ${response.body()?.size} times")
                    _busTimes.value = response.body() ?: emptyList()
                } else {
                    Log.e("RajbariViewModel", "Failed to fetch bus times. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Bustime>>, t: Throwable) {
                Log.e("RajbariViewModel", "Error fetching bus times", t)
            }
        })
    }

    fun addBusCounter(busCounter: BusCounter) {
        Log.d("RajbariViewModel", "Adding bus counter: $busCounter")
        RetrofitClient.busCounterApiService.addCounter(busCounter).enqueue(object : Callback<BusCounter> {
            override fun onResponse(call: Call<BusCounter>, response: Response<BusCounter>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Bus counter added successfully: ${response.body()}")
                    getBusCounters() // Refresh the list
                } else {
                    Log.e("RajbariViewModel", "Failed to add bus counter. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BusCounter>, t: Throwable) {
                Log.e("RajbariViewModel", "Error adding bus counter", t)
            }
        })
    }

    fun addBusTime(busTime: Bustime) {
        Log.d("RajbariViewModel", "Adding bus time: $busTime")
        RetrofitClient.bustimeApiService.addBusTime(busTime).enqueue(object : Callback<Bustime> {
            override fun onResponse(call: Call<Bustime>, response: Response<Bustime>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Bus time added successfully: ${response.body()}")
                    getBusTimes() // Refresh the list
                } else {
                    Log.e("RajbariViewModel", "Failed to add bus time. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Bustime>, t: Throwable) {
                Log.e("RajbariViewModel", "Error adding bus time", t)
            }
        })
    }

    fun getDonors() {
        Log.d("RajbariViewModel", "Fetching donors...")
        RetrofitClient.bloodDonorApiService.getDonors().enqueue(object : Callback<List<BloodDonor>> {
            override fun onResponse(call: Call<List<BloodDonor>>, response: Response<List<BloodDonor>>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Donors fetched successfully: ${response.body()?.size} donors")
                    _donors.value = response.body() ?: emptyList()
                } else {
                    Log.e("RajbariViewModel", "Failed to fetch donors. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<BloodDonor>>, t: Throwable) {
                Log.e("RajbariViewModel", "Error fetching donors", t)
            }
        })
    }

    fun addDonor(donor: BloodDonor) {
        Log.d("RajbariViewModel", "Adding donor: $donor")
        RetrofitClient.bloodDonorApiService.addDonor(donor).enqueue(object : Callback<BloodDonor> {
            override fun onResponse(call: Call<BloodDonor>, response: Response<BloodDonor>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Donor added successfully: ${response.body()}")
                    getDonors() // Refresh the list
                } else {
                    Log.e("RajbariViewModel", "Failed to add donor. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BloodDonor>, t: Throwable) {
                Log.e("RajbariViewModel", "Error adding donor", t)
            }
        })
    }

    fun getRequests() {
        Log.d("RajbariViewModel", "Fetching requests...")
        RetrofitClient.bloodRequestApiService.getRequests().enqueue(object : Callback<List<BloodRequest>> {
            override fun onResponse(call: Call<List<BloodRequest>>, response: Response<List<BloodRequest>>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Requests fetched successfully: ${response.body()?.size} requests")
                    _requests.value = response.body() ?: emptyList()
                } else {
                    Log.e("RajbariViewModel", "Failed to fetch requests. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<BloodRequest>>, t: Throwable) {
                Log.e("RajbariViewModel", "Error fetching requests", t)
            }
        })
    }

    fun addRequest(request: BloodRequest) {
        Log.d("RajbariViewModel", "Adding request: $request")
        RetrofitClient.bloodRequestApiService.addRequest(request).enqueue(object : Callback<BloodRequest> {
            override fun onResponse(call: Call<BloodRequest>, response: Response<BloodRequest>) {
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Request added successfully: ${response.body()}")
                    getRequests() // Refresh the list
                } else {
                    Log.e("RajbariViewModel", "Failed to add request. Code: ${response.code()}, Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<BloodRequest>, t: Throwable) {
                Log.e("RajbariViewModel", "Error adding request", t)
            }
        })
    }

    // Local register
    fun registerUser(
        username: String,
        email: String,
        password: String,
        imageUri: Uri?
    ): Boolean {
        if (!ValidationUtils.isValidEmail(email)) {
            return false
        }

        val finalImageUri = imageUri?.toString() ?: "man"
        val newUser = User(username = username, email = email, password = password, profileImageUrl = finalImageUri)
        users.add(newUser)
        return true
    }

    // Online register
    fun sendVerificationCode(user: User, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.sendVerificationCode(user)
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RajbariViewModel", "Failed to send verification code: $errorBody")
                    onResult(false, parseErrorMessage(errorBody))
                }
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Send verification code exception: ${e.message}", e)
                onResult(false, "An unexpected error occurred during registration.")
            }
        }
    }

    fun verifyAndRegister(username: String, email: String, code: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (!ValidationUtils.isValidUsername(username)) {
            onResult(false, "Invalid username. Username must be at least 4 characters long and contain only letters and numbers.")
            return
        }
        if (!ValidationUtils.isValidPassword(password)) {
            onResult(false, "Invalid password. Password must be at least 6 characters long.")
            return
        }
        viewModelScope.launch {
            try {
                Log.d("RajbariViewModel", "Verifying email: $email with code: $code")
                val verificationRequest = VerificationRequest(username, email, code, password)
                val response = RetrofitClient.apiService.verifyAndRegister(verificationRequest)
                if (response.isSuccessful) {
                    Log.d("RajbariViewModel", "Verification successful")
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RajbariViewModel", "Verification failed: $errorBody")
                    onResult(false, parseErrorMessage(errorBody))
                }
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Verification exception: ${e.javaClass.simpleName} - ${e.message}", e)
                onResult(false, "An unexpected error occurred during registration.")
            }
        }
    }

    // Server Login
    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (!ValidationUtils.isValidEmail(email)) {
            onResult(false, "Invalid email format.")
            return
        }
        if (password.isBlank()) {
            onResult(false, "Password cannot be empty.")
            return
        }
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email, password)
                val response = RetrofitClient.apiService.login(loginRequest)
                if (response.isSuccessful) {
                    loggedInUser = response.body()
                    onResult(true, null)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RajbariViewModel", "Login failed: $errorBody")
                    onResult(false, parseErrorMessage(errorBody))
                }
            } catch (e: Exception) {
                Log.e("RajbariViewModel", "Login exception: ${e.message}", e)
                onResult(false, "An unexpected error occurred during login.")
            }
        }
    }

    // Local Login
    fun loginLocal(input: String, password: String): Boolean {
        val matchedUser = users.find { user ->
            (user.username == input || user.email == input) &&
                    user.password == password
        }

        return if (matchedUser != null) {
            loggedInUser = matchedUser
            true
        } else {
            false
        }
    }

    fun logout() {
        loggedInUser = null
    }

    fun updateUserProfile(newUsername: String, newEmail: String) {
        loggedInUser = loggedInUser?.copy(
            username = newUsername,
            email = newEmail
        )
    }

    private fun parseErrorMessage(errorBody: String?): String {
        Log.d("RajbariViewModel", "Parsing error message from: $errorBody")
        errorBody ?: return "An unexpected error occurred."
        return try {
            val jsonObject = JSONObject(errorBody)
            jsonObject.getString("message")
        } catch (e: Exception) {
            "An unexpected error occurred."
        }
    }

    val sections = listOf(
        Section("শিক্ষা", "📚", "education"),
        Section("ডাক্তার", "🩺", "doctor"),
        Section("হাসপাতাল", "🏥", "hospital"),
        Section("রক্ত", "🩸", "blood"),
        Section("বাসের সময়সূচী", "🚌", "bus_schedule"),
        Section("ট্রেনের সময়সূচী", "🚆", "train_schedule"),
        Section("জরুরি নাম্বার ও হেল্পলাইন", "📞", "emergency"),
        Section("দর্শনীয় স্থান", "📸", "tourist_places"),
        Section("ওয়েবসাইট", "🌐", "websites"),
        Section("মিস্ত্রি", "🛠️", "mistry"),
        Section("গাড়ি ভাড়া", "🚖", "car_rent"),
        Section("হারানো ও পাওয়া", "🧳", "lost_found"),
        Section("কুরিয়ার সার্ভিস", "📦", "courier"),
        Section("চাকরি ও প্রশিক্ষণ", "💼", "jobs_training"),
        Section("হোটেল রেস্টুরেন্ট", "🍽️", "hotels_restaurants"),
        Section("শপিং", "🛍️", "Shopping"),
        Section("নার্সারি ", "🌾", "Nursery"),
        Section("কাছাকাছি মসজিদ", "🕌", "mosque_nearby")
    )
}