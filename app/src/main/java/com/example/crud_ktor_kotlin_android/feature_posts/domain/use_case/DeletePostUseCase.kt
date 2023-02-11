package com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case

import com.example.crud_ktor_kotlin_android.core.util.Resource
import com.example.crud_ktor_kotlin_android.feature_posts.domain.repository.PostsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: PostsRepository
) {
    suspend operator fun invoke(
        postId: String
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading<Unit>())
            val response = repository.deletePost(postId)
            emit(Resource.Success<Unit>(response))
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                emit(Resource.Error<Unit>(message = throwable.localizedMessage ?: "Error"))
            } else {
                emit(Resource.Error<Unit>(message = "Couldn't reach server . Check your internet connection"))
            }
        }
    }
}