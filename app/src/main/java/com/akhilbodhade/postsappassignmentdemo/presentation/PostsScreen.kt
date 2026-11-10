package com.akhilbodhade.practice1.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akhilbodhade.postsappassignmentdemo.presentation.PostsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    viewModel: PostsViewModel = hiltViewModel()
) {

    // Observe ViewModel state
    val state by viewModel.uiState
        .collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Posts Directory")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            when {

                // Loading state
                state.isLoading -> {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        CircularProgressIndicator()

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text("Loading posts...")
                    }
                }

                // Error state
                state.error != null -> {

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = state.error
                                ?: "Something went wrong",
                            color = MaterialTheme.colorScheme.error
                        )

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Button(
                            onClick = viewModel::fetchPostData
                        ) {
                            Text("Retry")
                        }
                    }
                }

                // Empty state
                state.success.isEmpty() -> {

                    Text("No posts available")
                }

                // Success state
                else -> {

                    LazyColumn(
                        verticalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        items(
                            items = state.success,
                            key = { post -> post.id }
                        ) { post ->

                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {

                                    Text(
                                        text = post.title,
                                        style = MaterialTheme
                                            .typography.titleMedium
                                    )

                                    Spacer(
                                        modifier = Modifier.height(8.dp)
                                    )

                                    Text(
                                        text = post.body,
                                        style = MaterialTheme
                                            .typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}