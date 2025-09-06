package com.example.rajbaricity.network

import com.example.rajbaricity.model.LoginRequest
import com.example.rajbaricity.model.User
import com.example.rajbaricity.model.VerificationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// RetrofitClient.kt তে BASE_URL
// private const val BASE_URL = "http://10.0.2.2:8080/"

// ApiService.kt (ফ্রন্টএন্ড)
interface ApiService {
    @POST("api/users/send-verification")
    suspend fun sendVerificationCode(@Body user: User): Response<ResponseBody> // ResponseBody ব্যবহার করে স্ট্রিং পড়া সহজ

    @POST("api/users/verify-and-register")
    suspend fun verifyAndRegister(@Body verificationRequest: VerificationRequest): Response<User>

    // login এর জন্যও একই রকম:
    @POST("api/users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<User>
}
