package com.example.rajbaricity.network

import com.example.rajbaricity.model.Doctor
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DoctorApiService {
    @GET("/api/doctors")
    suspend fun getDoctors(): List<Doctor>

    @POST("/api/doctors")
    suspend fun addDoctor(@Body doctor: Doctor): Doctor
}
