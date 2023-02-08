package com.example.crud_ktor_kotlin_android.feature_posts.presentation.add_update_post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crud_ktor_kotlin_android.core.util.Resource
import com.example.crud_ktor_kotlin_android.core.util.ValidationEvent
import com.example.crud_ktor_kotlin_android.feature_posts.domain.use_case.CreatePostUseCase
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
    private val createPostUseCase: CreatePostUseCase
) : ViewModel() {
    private val _state = mutableStateOf(AddUpdatePostState())
    val state: State<AddUpdatePostState> = _state

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

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


    private fun submitData() {
        val titleResult = validateField.execute(_state.value.title)
        val fileResult = validateImage.execute(_state.value.file)

        val hasError = listOf(
            titleResult,
            fileResult
        ).any { !it.successful }

        _state.value = state.value.copy(
            titleError = titleResult.errorMessage,
        )
        _state.value = state.value.copy(
            fileError = fileResult.errorColor
        )

        if (hasError) {
            return
        }

        viewModelScope.launch {
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
        }

    }
}