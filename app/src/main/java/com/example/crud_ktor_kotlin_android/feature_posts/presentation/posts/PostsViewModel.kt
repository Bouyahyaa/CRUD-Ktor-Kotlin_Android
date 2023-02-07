package com.example.crud_ktor_kotlin_android.feature_posts.presentation.posts

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_ktor_kotlin_android.core.util.Resource
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(PostsState())
    val state: State<PostsState> = _state

    init {
        getPosts()
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
}