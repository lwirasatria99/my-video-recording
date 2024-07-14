package com.example.myvideorecording.ui.videolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myvideorecording.ui.theme.MyVideoRecordingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoListActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyVideoRecordingTheme {
                VideoListScreen()
            }
        }
    }
}