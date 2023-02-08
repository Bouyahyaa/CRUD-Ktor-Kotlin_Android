package com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post

import android.net.Uri
import java.io.File

sealed class AddUpdatePostEvent {
    data class TitleChanged(val title: String) : AddUpdatePostEvent()
    data class  ImageChanged(val file: File) : AddUpdatePostEvent()
    data class OnPostImageUrlChanged(val uri: Uri) : AddUpdatePostEvent()
    object  Submit : AddUpdatePostEvent()
}
