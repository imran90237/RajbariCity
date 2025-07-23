package com.example.rajbaricity.ui

import android.net.Uri
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rajbaricity.model.Section
import com.example.rajbaricity.model.User
import com.example.rajbaricity.network.RetrofitClient
import com.example.rajbaricity.utils.ValidationUtils
import kotlinx.coroutines.launch

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

    val loggedInUserPhone: String?
        get() = loggedInUser?.phone

    val loggedInUserImage: String?
        get() = loggedInUser?.profileImageUri

    val loggedInUserImageUri: Uri?
        get() = loggedInUser?.profileImageUri?.let { Uri.parse(it) }

    // Local register
    fun registerUser(
        username: String,
        emailOrPhone: String,
        password: String,
        imageUri: Uri?
    ): Boolean {
        val email: String
        val phone: String

        if (ValidationUtils.isValidEmail(emailOrPhone)) {
            email = emailOrPhone
            phone = ""
        } else if (ValidationUtils.isValidPhone(emailOrPhone)) {
            phone = emailOrPhone
            email = ""
        } else {
            return false
        }

        if (users.any {
                it.username == username || it.email == email || it.phone == phone
            }) return false

        val finalImageUri = imageUri?.toString() ?: "man"
        val newUser = User(username, email, phone, password, finalImageUri)
        users.add(newUser)
        return true
    }

    // Online register
    fun registerUserOnline(user: User, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.register(user)
                if (response.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, response.errorBody()?.string() ?: "Registration failed")
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

    // Login
    fun login(input: String, password: String): Boolean {
        val matchedUser = users.find { user ->
            (user.username == input || user.email == input || user.phone == input) &&
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

    fun updateUserProfile(newUsername: String, newEmail: String, newPhone: String) {
        loggedInUser = loggedInUser?.copy(
            username = newUsername,
            email = newEmail,
            phone = newPhone
        )
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
