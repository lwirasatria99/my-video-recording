package com.example.myvideorecording.di

import com.example.myvideorecording.data.ApiService
import com.example.myvideorecording.data.VideoRepositoryImpl
import com.example.myvideorecording.domain.UploadVideoUseCase
import com.example.myvideorecording.domain.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideVideoRepository(
        api: ApiService
    ): VideoRepository {
        return VideoRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUploadVideoUseCase(
        repository: VideoRepository
    ): UploadVideoUseCase {
        return UploadVideoUseCase(repository)
    }
}
