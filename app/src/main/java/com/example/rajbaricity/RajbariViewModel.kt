package com.example.rajbaricity

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.rajbaricity.model.Section

class RajbariViewModel : ViewModel() {

    data class User(
        val username: String,
        val email: String,
        val phone: String,
        val password: String,
        val profileImageUri: String // Store as String
    )

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

    // Register user with either email or phone
    fun registerUser(
        username: String,
        emailOrPhone: String,
        password: String,
        imageUri: Uri?
    ): Boolean {
        val email: String
        val phone: String

        if (isValidEmail(emailOrPhone)) {
            email = emailOrPhone
            phone = ""
        } else if (isValidPhone(emailOrPhone)) {
            phone = emailOrPhone
            email = ""
        } else {
            return false // Invalid input
        }

        if (users.any {
                it.username == username || it.email == email || it.phone == phone
            }) return false // Already exists

        val finalImageUri = imageUri?.toString() ?: "man" // default image if null
        val newUser = User(username, email, phone, password, finalImageUri)
        users.add(newUser)
        return true
    }

    // Login with username or email or phone
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

    private fun isValidEmail(input: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(input).matches()

    private fun isValidPhone(input: String): Boolean =
        Patterns.PHONE.matcher(input).matches()

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
        Section("লোকাল মার্কেট", "🛍️", "local_market"),
        Section("ব্যবসা ও কৃষি সহায়তা", "🌾", "business_agriculture"),
        Section("কাছাকাছি মসজিদ", "🕌", "mosque_nearby")
    )
}
