package com.example.myvideorecording.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myvideorecording.ui.theme.MyVideoRecordingTheme
import com.example.myvideorecording.ui.videolist.VideoListActivity
import com.example.myvideorecording.util.createVideoFile
import com.example.myvideorecording.util.getUri
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val recordVideoLauncher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.CaptureVideo(),
                    onResult = { success ->
                        if (success) {
                            Toast.makeText(this, "Video Recorded: success", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Video Recorded: failed", Toast.LENGTH_SHORT).show()
                        }
                    })
            var videoFile by remember {
                mutableStateOf<File?>(null)
            }
            var videoUri by remember {
                mutableStateOf<Uri?>(null)
            }

            MyVideoRecordingTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                videoFile = createVideoFile()
                                videoUri = videoFile?.getUri(context = this@MainActivity)

                                videoUri?.let {
                                    recordVideoLauncher.launch(it)
                                }
                            }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.VideoCameraBack,
                                    contentDescription = null
                                )
                                Text(
                                    text = "Record Video",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Button(
                            onClick = {
                                val intent =
                                    Intent(this@MainActivity, VideoListActivity::class.java)
                                startActivity(intent)
                            }
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayCircleOutline,
                                    contentDescription = null
                                )
                                Text(
                                    text = "Play Video",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}