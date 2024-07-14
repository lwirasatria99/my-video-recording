package com.example.myvideorecording

import com.example.myvideorecording.data.ResultState
import com.example.myvideorecording.data.UploadResponse
import com.example.myvideorecording.domain.UploadVideoUseCase
import com.example.myvideorecording.domain.VideoRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.File

@ExperimentalCoroutinesApi
class UploadVideoUseCaseTest {

    @Mock
    private lateinit var repository: VideoRepository

    private lateinit var uploadVideoUseCase: UploadVideoUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        uploadVideoUseCase = UploadVideoUseCase(repository)
    }

    @Test
    fun `invoke should return success when repository returns success`(): Unit = runBlocking {
        // Arrange
        val file = File("path/to/video.mp4")
        val uploadResponse = UploadResponse(assetId = "123")
        val expectedResult = ResultState.Success(uploadResponse)
        `when`(repository.uploadVideo(file)).thenReturn(expectedResult)

        // Act
        val result = uploadVideoUseCase(file)

        // Assert
        assert(result is ResultState.Success)
        assert((result as ResultState.Success).data.assetId == "123")
        verify(repository).uploadVideo(file)
    }

    @Test
    fun `invoke should return error when repository returns error`(): Unit = runBlocking {
        // Arrange
        val file = File("path/to/video.mp4")
        val errorMessage = Exception("error")
        val expectedResult = ResultState.Error(errorMessage)
        `when`(repository.uploadVideo(file)).thenReturn(expectedResult)

        // Act
        val result = uploadVideoUseCase(file)

        // Assert
        assert(result is ResultState.Error)
        assert((result as ResultState.Error).exception == errorMessage)
        verify(repository).uploadVideo(file)
    }
}
