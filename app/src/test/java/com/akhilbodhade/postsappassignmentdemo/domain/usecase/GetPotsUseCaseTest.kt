package com.akhilbodhade.postsappassignmentdemo.domain.usecase

import com.akhilbodhade.postsappassignmentdemo.domain.model.Post
import com.akhilbodhade.postsappassignmentdemo.domain.repository.PostsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetPotsUseCaseTest {
    private val repository: PostsRepository = mockk()

    private val useCase = GetPostsUseCase(repository)

    @Test
    fun `invoke returns posts from repository`() = runTest {
        // Arrange
        val expectedPosts = listOf(
            Post(
                id = 1,
                title = "Test Title",
                body = "Test Body"
            )
        )

        coEvery {
            repository.getPosts()
        } returns expectedPosts

        // Act
        val result = useCase()

        // Assert
        assertEquals(
            expectedPosts,
            result
        )

        coVerify(exactly = 1) {
            repository.getPosts()
        }
    }

    @Test
    fun `invoke returns empty list`() =
        runTest {

            // Arrange
            coEvery {
                repository.getPosts()
            } returns emptyList()

            // Act
            val result = useCase()

            // Assert
            assertEquals(
                emptyList<Post>(),
                result
            )
        }
}