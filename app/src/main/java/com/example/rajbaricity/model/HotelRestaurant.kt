package com.example.rajbaricity.model

data class HotelRestaurant(
    val id: String = "",
    val name: String,
    val menu: String?,
    val address: String?,
    val phone: String?,
    val photoUrl: String? = ""
)