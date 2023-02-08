package com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case

import com.example.crud_ktor_kotlin_android.core.util.ValidationResult
import javax.inject.Inject

class ValidateField @Inject constructor() {

    fun execute(text: String): ValidationResult {
        if (text.trim().isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Required Field can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}