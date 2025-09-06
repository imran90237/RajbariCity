package com.example.rajbaricity.ui

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rajbaricity.model.LoginRequest
import com.example.rajbaricity.model.Section
import com.example.rajbaricity.model.User
import com.example.rajbaricity.model.VerificationRequest
import com.example.rajbaricity.network.RetrofitClient
import com.example.rajbaricity.utils.ValidationUtils
import kotlinx.coroutines.launch
import org.json.JSONObject

class RajbariViewModel : ViewModel() {

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