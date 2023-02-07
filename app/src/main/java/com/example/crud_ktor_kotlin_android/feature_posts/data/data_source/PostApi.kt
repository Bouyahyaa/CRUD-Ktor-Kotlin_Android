package com.example.crud_ktor_kotlin_android.feature_posts.data.data_source

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import retrofit2.Response
import retrofit2.http.GET

interface PostApi {
    @GET("api/posts/")
    suspend fun getPosts(): Response<List<Post>>
}