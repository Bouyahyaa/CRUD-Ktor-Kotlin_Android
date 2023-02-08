package com.example.crud_ktor_kotlin_android.core.util

sealed class ValidationEvent {
    data class Success(val message: String) : ValidationEvent()
    data class Error(val error: String) : ValidationEvent()
}