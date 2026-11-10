package com.akhilbodhade.postsappassignmentdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhilbodhade.postsappassignmentdemo.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(private val getPostsUseCase: GetPostsUseCase) :
    ViewModel() {

    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchPostData()
    }

    fun fetchPostData() {
        viewModelScope.launch {

            //Update loading state
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val posts = getPostsUseCase()
                _uiState.update { it.copy(isLoading = false, success = posts, error = null) }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Something went wrong"
                    )
                }

            }
        }
    }
}