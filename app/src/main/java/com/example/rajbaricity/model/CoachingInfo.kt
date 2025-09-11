package com.example.rajbaricity.model

data class CoachingInfo(
    val id: Long,
    val name: String,
    val features: String,
    val mapUrl: String,
    val imageUrl: String? = null
)
