package com.example.crud_ktor_kotlin_android.feature_posts.domain.models

data class Post(
    val id: String,
    val title: String,
    val image: String,
    var isLoading: Boolean
)
