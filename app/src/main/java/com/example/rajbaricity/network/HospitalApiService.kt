package com.example.rajbaricity.network

import com.example.rajbaricity.model.Hospital
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HospitalApiService {
    @GET("api/hospitals")
    suspend fun getHospitals(): List<Hospital>

    @POST("api/hospitals")
    suspend fun addHospital(@Body hospital: Hospital): Hospital
}