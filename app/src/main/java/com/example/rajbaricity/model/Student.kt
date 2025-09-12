package com.example.rajbaricity.model

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("name")
    val name: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("subject")
    val subject: String,

    @SerializedName("days")
    val days: String,

    @SerializedName("salary")
    val salary: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("thana")
    val thana: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("imageUrl")
    val imageUrl: String? = null,

    @SerializedName("likes")
    var likes: Int = 0
)