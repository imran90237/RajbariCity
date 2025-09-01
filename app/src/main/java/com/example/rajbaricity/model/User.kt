package com.example.rajbaricity.model

data class User(
    val id: Long = 0, // Added for consistency with backend
    val username: String,
    val email: String,
    val password: String,
    val profileImageUri: String? = null, // Made nullable
    val phone: String? = null // Added as nullable for future use
)