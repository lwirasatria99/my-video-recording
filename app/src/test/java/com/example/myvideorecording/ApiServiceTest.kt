package com.example.myvideorecording

import com.example.myvideorecording.data.ApiService
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    @Test
    fun `uploadVideo success`() = runBlocking {
        // Prepare the mock response
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"url": "http://example.com/video.mp4"}""")
        mockWebServer.enqueue(mockResponse)

        // Create request bodies
        val file = MultipartBody.Part.createFormData(
            "file", "video.mp4",
            RequestBody.create("video/*".toMediaTypeOrNull(), byteArrayOf())
        )
        val uploadPreset = RequestBody.create("text/plain".toMediaTypeOrNull(), "your_upload_preset")
        val publicId = RequestBody.create("text/plain".toMediaTypeOrNull(), "your_public_id")
        val apiKey = RequestBody.create("text/plain".toMediaTypeOrNull(), "your_api_key")

        // Call the API
        val response = apiService.uploadVideo(file, uploadPreset, publicId, apiKey)

        // Verify the response
        assert(response.isSuccessful)
        assert(response.body() != null)
    }

    @Test
    fun `uploadVideo failure`() = runBlocking {
        // Prepare the mock response
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody("""{"error": "Bad Request"}""")
        mockWebServer.enqueue(mockResponse)

        // Create request bodies
        val file = MultipartBody.Part.createFormData(
            "file", "video.mp4",
            RequestBody.create("video/*".toMediaTypeOrNull(), byteArrayOf())
        )
        val uploadPreset = RequestBody.create("text/plain".toMediaTypeOrNull(), "your_upload_preset")
        val publicId = RequestBody.create("text/plain".toMediaTypeOrNull(), "your_public_id")
        val apiKey = RequestBody.create("text/plain".toMediaTypeOrNull(), "your_api_key")

        // Call the API
        val response = apiService.uploadVideo(file, uploadPreset, publicId, apiKey)

        // Verify the response
        assert(response.isSuccessful.not())
        assert(response.errorBody()?.string()?.contains("Bad Request") == true)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}