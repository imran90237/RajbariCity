package com.example.rajbaricity.model

data class VerificationRequest(
    val username: String,
    val email: String,
    val code: String,
    val password: String
)
