package com.akhilbodhade.postsappassignmentdemo.domain.repository

import com.akhilbodhade.postsappassignmentdemo.domain.model.Post

interface PostsRepository {
    suspend fun getPosts(): List<Post>
}