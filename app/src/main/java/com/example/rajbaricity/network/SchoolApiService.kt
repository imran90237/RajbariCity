package com.example.rajbaricity.network

import com.example.rajbaricity.model.SchoolInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SchoolApiService {
    @GET("api/schools")
    suspend fun getAll(): List<SchoolInfo>

    @POST("api/schools")
    suspend fun create(@Body schoolInfo: SchoolInfo): SchoolInfo
}
