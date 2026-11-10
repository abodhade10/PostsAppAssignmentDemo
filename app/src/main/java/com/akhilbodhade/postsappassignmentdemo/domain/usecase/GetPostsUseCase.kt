package com.akhilbodhade.postsappassignmentdemo.domain.usecase

import com.akhilbodhade.postsappassignmentdemo.domain.model.Post
import com.akhilbodhade.postsappassignmentdemo.domain.repository.PostsRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(): List<Post> {
        return postsRepository.getPosts()
    }
}