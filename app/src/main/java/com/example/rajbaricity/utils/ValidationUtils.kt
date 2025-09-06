package com.example.rajbaricity.utils

import android.util.Patterns

object ValidationUtils {
    fun isValidEmail(input: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(input).matches()

    fun isValidPhone(input: String): Boolean =
        Patterns.PHONE.matcher(input).matches()

    fun isValidUsername(username: String): Boolean {
        return username.length >= 4 && username.matches("^[a-zA-Z0-9]*$".toRegex())
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
}
