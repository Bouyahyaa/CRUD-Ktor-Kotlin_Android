package com.example.crud_ktor_kotlin_android.feature_posts.data.data_source

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val api: PostApi
) {
    suspend fun getPosts(): List<Post> {
        val response = api.getPosts()
        return response.body()!!
    }
}