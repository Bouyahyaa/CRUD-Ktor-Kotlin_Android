package com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case

import android.util.Log
import com.example.crud_ktor_kotlin_android.core.util.Resource
import com.example.crud_ktor_kotlin_android.feature_posts.domain.models.Post
import com.example.crud_ktor_kotlin_android.feature_posts.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    operator fun invoke(): Flow<Resource<List<Post>>> = flow {
        try {
            emit(Resource.Loading<List<Post>>())
            val posts = repository.getPosts()
            emit(Resource.Success<List<Post>>(posts))
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                val error = JSONObject(throwable.response()?.errorBody()!!.string())
                emit(Resource.Error<List<Post>>(message = error["message"] as String))
            } else {
                emit(Resource.Error<List<Post>>(message = "Couldn't reach server . Check your internet connection"))
            }
        }
    }
}