package com.example.myvideorecording.domain

import com.example.myvideorecording.data.UploadResponse
import com.example.myvideorecording.data.ResultState
import java.io.File

interface VideoRepository {
    suspend fun uploadVideo(file: File): ResultState<UploadResponse>
}