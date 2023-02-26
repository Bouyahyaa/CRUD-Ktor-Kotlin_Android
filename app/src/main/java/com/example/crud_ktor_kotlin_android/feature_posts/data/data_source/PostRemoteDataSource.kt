package com.example.crud_ktor_kotlin_android.feature_posts.data.data_source

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val api: PostApi
) {
    suspend fun getPosts(): List<Post> {
        val response = api.getPosts()
        return response.body()!!
    }

    suspend fun createPost(
        postImage: MultipartBody.Part?,
        title: RequestBody,
    ) {
        val response = api.createPost(
            postImage = postImage,
            title = title
        )
        return response.body()!!
    }

    suspend fun updatePost(
        postId: String,
        postImage: MultipartBody.Part?,
        title: RequestBody,
    ) {
        val response = api.updatePost(
            postId = postId,
            postImage = postImage,
            title = title
        )
        return response.body()!!
    }

    suspend fun deletePost(postId: String) {
        return api.deletePost(postId).body()!!
    }
}