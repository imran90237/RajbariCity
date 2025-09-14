package com.example.rajbaricity.network

import com.example.rajbaricity.model.Nursery
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NurseryApiService {
    @GET("/api/nurseries")
    suspend fun getAllNurseries(): List<Nursery>

    @POST("/api/nurseries")
    suspend fun createNursery(@Body nursery: Nursery): Nursery
}
