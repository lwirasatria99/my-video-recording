package com.example.myvideorecording.domain

import com.example.myvideorecording.data.ResultState
import com.example.myvideorecording.data.UploadResponse
import java.io.File
import javax.inject.Inject

class UploadVideoUseCase @Inject constructor(
    private val repository: VideoRepository
) {
    suspend operator fun invoke(file: File): ResultState<UploadResponse> {
        return repository.uploadVideo(file)
    }
}
