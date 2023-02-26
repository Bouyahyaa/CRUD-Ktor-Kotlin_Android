package com.example.crud_ktor_kotlin_android.feature_posts.domain.repository

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PostsRepository {
    suspend fun getPosts(): List<Post>
    suspend fun createPost(
        postImage: MultipartBody.Part?,
        title: RequestBody
    )

    suspend fun updatePost(
        postId: String,
        postImage: MultipartBody.Part?,
        title: RequestBody
    )

    suspend fun deletePost(postId: String)
}