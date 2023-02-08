package com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case

import androidx.compose.ui.graphics.Color
import com.example.crud_ktor_kotlin_android.core.util.ValidationResult
import java.io.File
import javax.inject.Inject

class ValidateImage @Inject constructor() {

    fun execute(imageSelectedFile: File?): ValidationResult {
        if (imageSelectedFile == null) {
            return ValidationResult(
                successful = false,
                errorColor = Color.Red
            )
        }

        return ValidationResult(
            successful = true,
            errorColor = Color.Transparent
        )
    }
}