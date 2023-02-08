package com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post

import android.net.Uri
import androidx.compose.ui.graphics.Color
import java.io.File

data class AddUpdatePostState(
    val file: File? = null,
    val fileError: Color = Color.Transparent,
    val ImageUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val title: String = "",
    val titleError: String? = null,
)
