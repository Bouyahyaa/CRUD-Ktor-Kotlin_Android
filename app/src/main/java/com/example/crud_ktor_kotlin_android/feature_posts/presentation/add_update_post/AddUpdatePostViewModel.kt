package com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_ktor_kotlin_android.BuildConfig
import com.example.crud_ktor_kotlin_android.CrudApplication
import com.example.crud_ktor_kotlin_android.core.util.Constants.toFormattedUrlGet
import com.example.crud_ktor_kotlin_android.core.util.Resource
import com.example.crud_ktor_kotlin_android.core.util.ValidationEvent
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.CreatePostUseCase
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.UpdatePostUseCase
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.ValidateField
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.ValidateImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class AddUpdatePostViewModel @Inject constructor(
    private val validateField: ValidateField,
    private val validateImage: ValidateImage,
    private val createPostUseCase: CreatePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(AddUpdatePostState())
    val state: State<AddUpdatePostState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val postId = savedStateHandle.get<String>("postId")
    private val postTitle = savedStateHandle.get<String>("title")
    private val postImage =
        BuildConfig.SERVER_BASE_URL + savedStateHandle.get<String>("image")?.toFormattedUrlGet()

    init {
        if (postId != null) {
            getPost()
        }
    }

    fun onEvent(event: AddUpdatePostEvent) {
        when (event) {
            is AddUpdatePostEvent.TitleChanged -> {
                _state.value = state.value.copy(title = event.title)
            }
            is AddUpdatePostEvent.ImageChanged -> {
                _state.value = state.value.copy(file = event.file)
            }
            is AddUpdatePostEvent.OnPostImageUrlChanged -> {
                _state.value = state.value.copy(
                    ImageUri = event.uri,
                )
            }
            is AddUpdatePostEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun getPost() {
        val uri = Uri.parse(postImage)
        _state.value = state.value.copy(
            file = null,
            title = postTitle!!,
            ImageUri = uri
        )
    }


    private fun submitData() {
        val titleResult = validateField.execute(_state.value.title)
        val fileResult = validateImage.execute(_state.value.file)

        val hasError = if (postId.isNullOrBlank()) {
            listOf(
                titleResult,
                fileResult
            ).any { !it.successful }
        } else {
            listOf(
                titleResult,
            ).any { !it.successful }
        }

        _state.value = state.value.copy(
            titleError = titleResult.errorMessage,
        )

        if (postId.isNullOrBlank()) {
            _state.value = state.value.copy(
                fileError = fileResult.errorColor
            )
        }

        if (hasError) {
            return
        }

        viewModelScope.launch {
            if (postId.isNullOrBlank()) {
                createPostUseCase.invoke(
                    if (_state.value.file != null) {
                        MultipartBody.Part.createFormData(
                            "file",
                            _state.value.file!!.name,
                            _state.value.file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    } else null,
                    _state.value.title.toRequestBody("application/json".toMediaTypeOrNull()),
                )
                    .collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    error = ""
                                )
                            }

                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    error = ""
                                )
                                validationEventChannel.send(ValidationEvent.Success("Success"))
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    error = result.message!!,
                                )
                                validationEventChannel.send(ValidationEvent.Error(_state.value.error))
                            }
                        }
                    }
            } else {
                updatePostUseCase.invoke(
                    postId,
                    if (_state.value.file != null) {
                        MultipartBody.Part.createFormData(
                            "file",
                            _state.value.file!!.name,
                            _state.value.file!!.asRequestBody("image/*".toMediaTypeOrNull())
                        )
                    } else null,
                    _state.value.title.toRequestBody("application/json".toMediaTypeOrNull()),
                )
                    .collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    isLoading = true,
                                    error = ""
                                )
                            }

                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    error = ""
                                )
                                validationEventChannel.send(ValidationEvent.Success("Success"))
                            }

                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    error = result.message!!,
                                )
                                validationEventChannel.send(ValidationEvent.Error(_state.value.error))
                            }
                        }
                    }
            }

        }
    }
}