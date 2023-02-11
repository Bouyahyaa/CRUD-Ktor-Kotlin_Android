package com.example.crud_ktor_kotlin_android.feature_posts.data.data_source

import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PostApi {
    @GET("api/posts/")
    suspend fun getPosts(): Response<List<Post>>

    @Multipart
    @POST("/api/posts/")
    suspend fun createPost(
        @Part postImage: MultipartBody.Part?,
        @Part("title") title: RequestBody,
    ): Response<Unit>

    @DELETE("/api/posts/{id}")
    suspend fun deletePost(
        @Path("id") postId: String,
    ): Response<Unit>
}