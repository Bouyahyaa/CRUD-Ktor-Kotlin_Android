package com.example.crud_ktor_kotlin_android.feature_posts.domain.repository

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}