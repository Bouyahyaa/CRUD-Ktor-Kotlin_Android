package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_ktor_kotlin_android.core.util.Resource
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.DeletePostUseCase
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PostsState())
    val state: State<PostsState> = _state

    init {
        getPosts()
    }


    fun onEvent(event: PostEvent) {
        when (event) {
            is PostEvent.OnDeletePost -> {
                deletePost(postId = event.postId)
            }
        }
    }

    private fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase.invoke().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("dataPosts", "${result.data}")
                        _state.value = PostsState(
                            posts = result.data ?: emptyList(),
                        )
                    }

                    is Resource.Error -> _state.value = PostsState(
                        error = result.message ?: "An unexpected error occur ",
                    )

                    is Resource.Loading -> {
                        Log.e("dataPosts", "${result.data}")
                        _state.value = PostsState(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun deletePost(postId: String) {
        viewModelScope.launch {
            deletePostUseCase.invoke(postId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Log.e("dataPost", "${result.data}")
                        val posts = _state.value.posts.filter {
                            it.id != postId
                        }
                        _state.value = PostsState(
                            posts = posts,
                        )
                    }

                    is Resource.Error -> _state.value = state.value.copy(
                        error = result.message ?: "An unexpected error occur ",
                    )

                    is Resource.Loading -> {
                        Log.e("dataPost", "${result.data}")
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}