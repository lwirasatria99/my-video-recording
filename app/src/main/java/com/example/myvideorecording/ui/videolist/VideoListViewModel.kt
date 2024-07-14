package com.example.myvideorecording.ui.videolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvideorecording.data.UploadResponse
import com.example.myvideorecording.domain.UploadVideoUseCase
import com.example.myvideorecording.data.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val uploadVideoUseCase: UploadVideoUseCase
) : ViewModel() {

    private val _uploadState = MutableStateFlow<ResultState<UploadResponse>?>(null)
    val uploadState: StateFlow<ResultState<UploadResponse>?> get() = _uploadState

    fun uploadVideo(file: File) {
        viewModelScope.launch {
            _uploadState.value = ResultState.Loading
            _uploadState.value = uploadVideoUseCase.invoke(file)
        }
    }

}
