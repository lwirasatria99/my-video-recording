package com.example.myvideorecording.ui.videolist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myvideorecording.data.ResultState
import com.example.myvideorecording.util.getVideoFiles
import com.example.myvideorecording.util.openVideoPlayer
import java.io.File

@Composable
fun VideoListScreen(
    viewModel: VideoListViewModel = hiltViewModel()
) {
    val uploadState by viewModel.uploadState.collectAsState()
    val context = LocalContext.current
    val videos = remember { mutableStateListOf<File>() }

    LaunchedEffect(Unit) {
        videos.addAll(context.getVideoFiles())
    }

    LaunchedEffect(key1 = uploadState) {
        when (uploadState) {
            is ResultState.Loading -> {}
            is ResultState.Error -> {
                val result = uploadState as ResultState.Error
                Toast.makeText(
                    context,
                    "Video upload failed: ${result.exception.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            is ResultState.Success -> {
                Toast.makeText(context, "Video upload succeeded", Toast.LENGTH_LONG).show()
            }
            null -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (uploadState is ResultState.Loading) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(color = Color.Green)
                Text(text = "Uploading... Takes time about 1 minute")
            }
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Recorded Videos", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
                videos.forEach { video ->
                    VideoListItem(
                        video = video,
                        onUpload = { file ->
                            viewModel.uploadVideo(file)
                        },
                        onPlay = {
                            val uri = FileProvider.getUriForFile(
                                context,
                                context.applicationContext.packageName + ".fileprovider",
                                it
                            )
                            uri.openVideoPlayer(context)
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

}

@Composable
fun VideoListItem(
    video: File,
    onUpload: (File) -> Unit,
    onPlay: (File) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onUpload(video) }
            .padding(16.dp),
    ) {
        Text(
            text = video.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row {
            Button(
                onClick = { onPlay(video) },
                modifier = Modifier
                    .padding(end = 8.dp)
                    .wrapContentWidth()
            ) {
                Text("Play")
            }
            Button(
                onClick = { onUpload(video) },
                modifier = Modifier
                    .wrapContentWidth()
            ) {
                Text("Upload")
            }
        }
    }
}
