package com.example.myvideorecording.data

import com.example.myvideorecording.domain.VideoRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val api: ApiService
) : VideoRepository {

    override suspend fun uploadVideo(file: File): ResultState<UploadResponse> {
        val myApiKey = "723479291592737"
        val myUploadPreset = "ml_default"

        return try {
            val requestFile = file.asRequestBody("video/mp4".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val uploadPreset = myUploadPreset.toRequestBody("text/plain".toMediaTypeOrNull())
            val publicId = UUID.randomUUID().toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val apiKey = myApiKey.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = api.uploadVideo(body, uploadPreset, publicId, apiKey)
            if (response.isSuccessful) {
                response.body()?.let { ResultState.Success(it) }
                    ?: ResultState.Error(Exception("Response body is null"))
            } else {
                ResultState.Error(Exception("Upload failed"))
            }
        } catch (e: IOException) {
            ResultState.Error(Exception("I/O error: ${e.message}"))
        } catch (e: HttpException) {
            ResultState.Error(Exception("HTTP error: ${e.message}"))
        } catch (e: Exception) {
            ResultState.Error(Exception("Unknown error: ${e.message}"))
        }

    }
}