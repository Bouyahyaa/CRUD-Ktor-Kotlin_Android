package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post

data class PostsState(
    val error: String = "",
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
)
