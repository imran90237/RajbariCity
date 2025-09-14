package com.example.rajbaricity.network

import com.example.rajbaricity.model.Courier
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CourierApiService {
    @GET("/api/couriers")
    suspend fun getCouriers(): List<Courier>

    @POST("/api/couriers")
    suspend fun addCourier(@Body courier: Courier): Courier
}
