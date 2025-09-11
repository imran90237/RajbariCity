package com.example.rajbaricity.model
data class BloodRequest(
    val id: Long = 0,
    val name: String,
    val bloodGroup: String,
    val bagCount: String,
    val dateTime: String,
    val phone: String,
    val hospital: String,
    val details: String
)
