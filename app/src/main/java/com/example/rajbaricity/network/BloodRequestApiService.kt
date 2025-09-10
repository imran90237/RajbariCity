package com.example.rajbaricity.network

import com.example.rajbaricity.model.BloodRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BloodRequestApiService {

    @GET("api/blood_requests")
    fun getRequests(): Call<List<BloodRequest>>

    @POST("api/blood_requests")
    fun addRequest(@Body request: BloodRequest): Call<BloodRequest>
}


