package com.example.myvideorecording.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myvideorecording.ui.testing.VideoRecordScreen
import com.example.myvideorecording.ui.videolist.VideoListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "testing") {
        composable("testing") { VideoRecordScreen(navController) }
        composable("video_list") { VideoListScreen() }
    }
}
