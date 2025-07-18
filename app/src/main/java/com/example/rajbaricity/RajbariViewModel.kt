package com.example.rajbaricity

import android.net.Uri
import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.rajbaricity.model.Section

class RajbariViewModel : ViewModel() {

    data class User(
        val name: String,
        val emailOrPhone: String,
        val password: String,
        val profileImageUri: String
    )

    private val users = mutableStateListOf<User>()

    var loggedInUser by mutableStateOf<User?>(null)
        private set

    // নতুন প্রপার্টি
    val isRegistered: Boolean
        get() = users.isNotEmpty()

    val loggedInUserName: String?
        get() = loggedInUser?.name

    val loggedInUserEmail: String?
        get() = loggedInUser?.emailOrPhone

    val loggedInUserImage: String?
        get() = loggedInUser?.profileImageUri

    fun registerUser(
        name: String,
        emailOrPhone: String,
        password: String,
        imageUri: Uri?
    ): Boolean {
        if (!isValidEmail(emailOrPhone) && !isValidPhone(emailOrPhone)) {
            return false
        }
        if (users.any { it.emailOrPhone == emailOrPhone }) {
            return false
        }
        val finalImageUri = imageUri?.toString() ?: "man"
        val newUser = User(name, emailOrPhone, password, finalImageUri)
        users.add(newUser)
        return true
    }

    fun login(emailOrPhone: String, password: String): Boolean {
        val matchedUser = users.find { it.emailOrPhone == emailOrPhone && it.password == password }
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

    fun updateUserProfile(newName: String, newEmail: String) {
        loggedInUser = loggedInUser?.copy(
            name = newName,
            emailOrPhone = newEmail
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
