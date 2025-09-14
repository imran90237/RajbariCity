package com.example.rajbaricity.model

import androidx.annotation.DrawableRes

data class Hospital(
    val id: Long? = null,
    val name: String,
    val address: String,
    val phone: String,
    val hours: String,
    val hasEmergency: Boolean,
    val mapUrl: String,
    val photoUrl: String?,
    @DrawableRes val photoResId: Int? = null,
    val type: String
)