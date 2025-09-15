package com.example.rajbaricity.model

data class LostAndFound(
    val title: String = "",
    val description: String = "",
    val contactName: String = "",
    val contactPhone: String = "",
    val photoUrl: String = "",
    val isLost: Boolean = true
)
