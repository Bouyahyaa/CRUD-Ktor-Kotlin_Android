package com.example.crud_ktor_kotlin_android.feature_posts.data.repository

import com.example.crud_ktor_kotlin_android.feature_posts.data.data_source.PostRemoteDataSource
import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import com.example.crud_ktor_kotlin_android.feature_posts.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    private val postRemoteDataSource: PostRemoteDataSource
) : PostsRepository {
    override suspend fun getPosts(): List<Post> {
        return postRemoteDataSource.getPosts()
    }
}