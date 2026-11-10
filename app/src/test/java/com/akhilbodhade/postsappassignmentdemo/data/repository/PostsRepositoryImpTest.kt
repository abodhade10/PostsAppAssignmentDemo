package com.akhilbodhade.postsappassignmentdemo.data.repository

import com.akhilbodhade.postsappassignmentdemo.data.remote.PostApi
import com.akhilbodhade.postsappassignmentdemo.data.remote.PostDto
import com.akhilbodhade.postsappassignmentdemo.domain.model.Post
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.jupiter.api.fail

class PostsRepositoryImpTest {

    val api: PostApi = mockk()
    val repository = PostsRepositoryImpl(api)

    @Test
    fun `getPosts returns mapped domain models`() = runTest {

        //Arrange
        val dtoList = listOf(
            PostDto(
                userId = 1,
                id = 1,
                title = "Test Title",
                body = "Test Body"
            )
        )

        val expectedPosts = listOf(
            Post(
                id = 1,
                title = "Test Title",
                body = "Test Body"
            )
        )

        coEvery {
            api.getPosts()
        } returns dtoList

        // Act
        val result = repository.getPosts()

        // Assert
        assertEquals(expectedPosts, result)

        coVerify(exactly = 1) {
            api.getPosts()
        }
    }

    @Test
    fun `getPosts returns empty list`() =
        runTest {

            // Arrange
            coEvery {
                api.getPosts()
            } returns emptyList()

            // Act
            val result = repository.getPosts()

            // Assert
            assertTrue(result.isEmpty())
        }

    @Test
    fun `getPosts propagates api exception`() = runTest {

        // Arrange
        coEvery {
            api.getPosts()
        } throws RuntimeException("Network error")

        // Act and Assert
        try {

            repository.getPosts()

            fail("Expected RuntimeException")

        } catch (e: RuntimeException) {

            assertEquals(
                "Network error",
                e.message
            )
        }
    }
}