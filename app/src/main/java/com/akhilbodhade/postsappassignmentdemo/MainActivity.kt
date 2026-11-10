package com.akhilbodhade.postsappassignmentdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.akhilbodhade.postsappassignmentdemo.ui.theme.PostsAppAssignmentDemoTheme
import com.akhilbodhade.practice1.presentation.PostsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostsAppAssignmentDemoTheme {
                PostsScreen()
            }
        }
    }
}
