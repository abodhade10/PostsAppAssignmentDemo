package com.akhilbodhade.postsappassignmentdemo.data.repository

import com.akhilbodhade.postsappassignmentdemo.data.mapper.toDomain
import com.akhilbodhade.postsappassignmentdemo.data.remote.PostApi
import com.akhilbodhade.postsappassignmentdemo.domain.model.Post
import com.akhilbodhade.postsappassignmentdemo.domain.repository.PostsRepository
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(private val postApi: PostApi) : PostsRepository{
    override
    suspend fun getPosts(): List<Post> {
        return postApi.getPosts().map { it.toDomain() }
    }
}