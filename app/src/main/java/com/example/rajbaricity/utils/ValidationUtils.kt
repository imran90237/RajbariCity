package com.example.rajbaricity.utils

import android.util.Patterns

object ValidationUtils {
    fun isValidEmail(input: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(input).matches()

    fun isValidPhone(input: String): Boolean =
        Patterns.PHONE.matcher(input).matches()
}
