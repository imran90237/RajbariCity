package com.example.rajbaricity.model

data class User(
    val id: Long = 0,
    val username: String,
    val email: String,
    val password: String? = null, // পাসওয়ার্ড এখন nullable
    val profileImageUrl: String? = null,
    val phone: String? = null,
    val verified: Boolean = false
)