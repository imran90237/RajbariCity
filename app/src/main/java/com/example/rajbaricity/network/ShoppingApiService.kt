package com.example.rajbaricity.network

import com.example.rajbaricity.model.Shopping
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShoppingApiService {
    @GET("/api/products")
    suspend fun getShoppings(): List<Shopping>

    @POST("/api/products")
    suspend fun addShopping(@Body shopping: Shopping): Shopping
}
