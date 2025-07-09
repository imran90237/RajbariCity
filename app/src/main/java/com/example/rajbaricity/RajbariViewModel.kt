package com.example.rajbaricity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.rajbaricity.model.Section

class RajbariViewModel : ViewModel() {

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

    var loggedInUserName by mutableStateOf<String?>(null)
        private set

    fun login(username: String) {
        loggedInUserName = username
    }
}
