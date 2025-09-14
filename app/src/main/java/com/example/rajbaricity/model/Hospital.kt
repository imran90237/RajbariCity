package com.example.rajbaricity.model

data class Hospital(
    val id: Long? = null,
    val name: String,
    val address: String,
    val phone: String,
    val hours: String,
    val hasEmergency: Boolean,
    val mapUrl: String,
    val photoUrl: String?,
    val type: String
)