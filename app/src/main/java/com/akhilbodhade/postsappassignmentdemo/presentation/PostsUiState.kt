package com.akhilbodhade.postsappassignmentdemo.presentation

import com.akhilbodhade.postsappassignmentdemo.domain.model.Post

data class PostsUiState(
     val isLoading: Boolean = false,
     val success: List<Post> = emptyList(),
     val error: String? = null
)