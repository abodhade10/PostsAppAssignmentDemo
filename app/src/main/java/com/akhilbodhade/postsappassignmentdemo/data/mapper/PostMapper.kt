package com.akhilbodhade.postsappassignmentdemo.data.mapper

import com.akhilbodhade.postsappassignmentdemo.data.remote.PostDto
import com.akhilbodhade.postsappassignmentdemo.domain.model.Post

fun PostDto.toDomain(): Post {
    return Post(
        id = id,
        title = title,
        body = body
    )
}