package com.example.rajbaricity.network

import com.example.rajbaricity.model.User
import com.example.rajbaricity.model.VerificationRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/register")
    suspend fun register(@Body user: User): Response<ResponseBody>

    @POST("api/users/send-verification")
    suspend fun sendVerificationCode(@Body user: User): Response<ResponseBody>

    @POST("api/users/verify-and-register")
    suspend fun verifyAndRegister(@Body verificationRequest: VerificationRequest): Response<ResponseBody>
}
