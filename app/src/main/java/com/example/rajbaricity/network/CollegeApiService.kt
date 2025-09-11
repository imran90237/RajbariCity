package com.example.rajbaricity.network

import com.example.rajbaricity.model.CollegeInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CollegeApiService {
    @GET("api/college")
    suspend fun getAll(): List<CollegeInfo>

    @POST("api/college")
    suspend fun create(@Body collegeInfo: CollegeInfo): CollegeInfo
}
