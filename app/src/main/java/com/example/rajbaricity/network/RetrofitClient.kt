package com.example.rajbaricity.network

import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

object RetrofitClient {
    // Ensure you are using the correct IP address for your local server.
    // 10.0.2.2 is typically used for the Android emulator to connect to the host machine's localhost.
    private const val BASE_URL = "http://10.0.2.2:8081/"

    // Create a logging interceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create a CookieJar to handle session cookies
    private val cookieManager = CookieManager()
    private val cookieJar = JavaNetCookieJar(cookieManager)

    // Create an Auth interceptor
    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            // Add your auth token here if you have one
            // .addHeader("Authorization", "Bearer YOUR_AUTH_TOKEN")
            .build()
        chain.proceed(request)
    }

    // Create an OkHttpClient and add the interceptor and cookie jar
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor) // Add the auth interceptor
        .build()

    // Create a lazy-initialized Retrofit instance
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // Use the custom OkHttpClient
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create a lazy-initialized ApiService instance
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    val bloodDonorApiService: BloodDonorApiService by lazy {
        retrofit.create(BloodDonorApiService::class.java)
    }

    val bloodRequestApiService: BloodRequestApiService by lazy {
        retrofit.create(BloodRequestApiService::class.java)
    }

    val busCounterApiService: BusCounterApiService by lazy {
        retrofit.create(BusCounterApiService::class.java)
    }

    val bustimeApiService: BustimeApiService by lazy {
        retrofit.create(BustimeApiService::class.java)
    }

    val carApiService: CarApiService by lazy {
        retrofit.create(CarApiService::class.java)
    }

    val coachingApiService: CoachingApiService by lazy {
        retrofit.create(CoachingApiService::class.java)
    }

    val collegeApiService: CollegeApiService by lazy {
        retrofit.create(CollegeApiService::class.java)
    }

    val madrasaApiService: MadrasaApiService by lazy {
        retrofit.create(MadrasaApiService::class.java)
    }

    val schoolApiService: SchoolApiService by lazy {
        retrofit.create(SchoolApiService::class.java)
    }

    val studentApiService: StudentApiService by lazy {
        retrofit.create(StudentApiService::class.java)
    }

    val teacherApiService: TeacherApiService by lazy {
        retrofit.create(TeacherApiService::class.java)
    }

    val hospitalApiService: HospitalApiService by lazy {
        retrofit.create(HospitalApiService::class.java)
    }

}
