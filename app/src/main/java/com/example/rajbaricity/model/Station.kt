package com.example.rajbaricity.model

data class Station(
    val name: String,
    val imageRes: Int,
    val trains: List<TrainInfo> = emptyList()
)
