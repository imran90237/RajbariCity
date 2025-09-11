package com.example.rajbaricity.network

import com.example.rajbaricity.model.CarInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CarApiService {
    @GET("/api/cars")
    fun getCars(): Call<List<CarInfo>>

    @POST("/api/cars")
    fun addCar(@Body carInfo: CarInfo): Call<CarInfo>
}
