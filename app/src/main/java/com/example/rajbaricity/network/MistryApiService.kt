package com.example.rajbaricity.network

import com.example.rajbaricity.model.Mistry
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MistryApiService {
    @GET("/api/mistrys")
    suspend fun getMistries(): List<Mistry>

    @POST("/api/mistrys")
    suspend fun addMistry(@Body mistry: Mistry): Mistry
}
