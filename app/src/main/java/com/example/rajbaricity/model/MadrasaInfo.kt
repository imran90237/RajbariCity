package com.example.rajbaricity.model

data class MadrasaInfo(
    val id: Long = 0,
    val name: String,
    val established: String,
    val features: String,
    val mapUrl: String,
    val imageUrl: String? = null,
    val type: String // "Qawmi" or "Alia"
)
