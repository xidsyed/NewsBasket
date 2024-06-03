package com.loc.newsapp.data.local.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(BookmarkState())
    val state = _state.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.selectArticles().onEach { articles ->
            _state.update { state ->
                state.copy(articles = articles.asReversed())
            }
        }.launchIn(viewModelScope)
    }
}