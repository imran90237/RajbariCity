package com.example.rajbaricity.network

import com.example.rajbaricity.model.CoachingInfo
import retrofit2.Response
import retrofit2.http.*

interface CoachingApiService {
    @GET("api/coaching")
    suspend fun getAll(): List<CoachingInfo>

    @GET("api/coaching/{id}")
    suspend fun getById(@Path("id") id: Long): Response<CoachingInfo>

    @POST("api/coaching")
    suspend fun create(@Body coaching: CoachingInfo): CoachingInfo

    @PUT("api/coaching/{id}")
    suspend fun update(@Path("id") id: Long, @Body coaching: CoachingInfo): Response<CoachingInfo>

    @DELETE("api/coaching/{id}")
    suspend fun delete(@Path("id") id: Long): Response<Void>
}
