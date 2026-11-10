package com.akhilbodhade.postsappassignmentdemo.presentation

import com.akhilbodhade.postsappassignmentdemo.domain.model.Post
import com.akhilbodhade.postsappassignmentdemo.domain.usecase.GetPostsUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase: GetPostsUseCase = mockk()

    private val posts = listOf(
        Post(
            id = 1,
            title = "Test Title",
            body = "Test Body"
        )
    )

    @Test
    fun `fetchPosts success updates ui state`() =
        runTest(mainDispatcherRule.dispatcher) {

            // Arrange
            coEvery {
                useCase()
            } returns posts

            // Act
            val viewModel = PostsViewModel(useCase)

            advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value

            assertEquals(posts, state.success)

            assertFalse(state.isLoading)

            assertNull(state.error)
        }

    @Test
    fun `fetchPosts failure updates error state`() =
        runTest(mainDispatcherRule.dispatcher) {

            // Arrange
            coEvery {
                useCase()
            } throws RuntimeException("Network error")

            // Act
            val viewModel = PostsViewModel(useCase)

            advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value

            assertEquals(
                "Network error",
                state.error
            )

            assertFalse(state.isLoading)

            assertTrue(state.success.isEmpty())
        }

    @Test
    fun `retry after failure loads posts successfully`() =
        runTest(mainDispatcherRule.dispatcher) {

            // Arrange
            coEvery {
                useCase()
            } throws RuntimeException("Network error") andThen posts

            // Act
            val viewModel = PostsViewModel(useCase)

            advanceUntilIdle()

            // First request fails
            assertEquals(
                "Network error",
                viewModel.uiState.value.error
            )

            // Retry
            viewModel.fetchPostData()

            advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value

            assertEquals(posts, state.success)

            assertNull(state.error)

            assertFalse(state.isLoading)

            coVerify(exactly = 2) {
                useCase()
            }
        }

    @Test
    fun `fetchPosts empty response updates ui state`() =
        runTest(mainDispatcherRule.dispatcher) {

            // Arrange
            coEvery {
                useCase()
            } returns emptyList()

            // Act
            val viewModel = PostsViewModel(useCase)

            advanceUntilIdle()

            // Assert
            val state = viewModel.uiState.value

            assertTrue(state.success.isEmpty())

            assertFalse(state.isLoading)

            assertNull(state.error)
        }
}