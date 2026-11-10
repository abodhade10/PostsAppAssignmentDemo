package com.akhilbodhade.postsappassignmentdemo.data.remote

import retrofit2.http.GET

interface PostApi {

    @GET("posts")
    suspend fun getPosts(): List<PostDto>

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    }
}