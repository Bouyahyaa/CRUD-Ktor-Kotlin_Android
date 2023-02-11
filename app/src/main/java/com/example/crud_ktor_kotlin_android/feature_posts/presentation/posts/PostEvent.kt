package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts

sealed class PostEvent {
    data class OnDeletePost(val postId: String) : PostEvent()
}
