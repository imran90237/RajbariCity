package com.example.rajbaricity.network

import com.example.rajbaricity.model.BloodDonor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BloodDonorApiService {

    @GET("api/blood_donors")
    fun getDonors(): Call<List<BloodDonor>>

    @POST("api/blood_donors")
    fun addDonor(@Body donor: BloodDonor): Call<BloodDonor>
}
