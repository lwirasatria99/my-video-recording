package com.example.myvideorecording.ui.testing

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.myvideorecording.ui.NavGraph
import com.example.myvideorecording.ui.theme.MyVideoRecordingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoRecordActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, cameraxPermissions, 0
            )
        }
        setContent {
            MyVideoRecordingTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }

    private val cameraxPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
    )

    private fun hasRequiredPermissions(): Boolean {
        return cameraxPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}