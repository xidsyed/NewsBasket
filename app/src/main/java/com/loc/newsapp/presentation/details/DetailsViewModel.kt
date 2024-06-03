package com.loc.newsapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val handle: SavedStateHandle
) : ViewModel() {

    private var _sideEffect: MutableStateFlow<String?>? = MutableStateFlow(null)
    val sideEffect = _sideEffect?.asStateFlow()
    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked = _isBookmarked.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val article = handle.get<Article>("article") as Article
            _isBookmarked.update { newsUseCases.selectArticle(article.url) != null }
        }
    }

    fun onDetailsEvent(event: DetailsEvent) {
        viewModelScope.launch {
            when (event) {
                is DetailsEvent.DeleteUpsertArticleEvent -> withContext(Dispatchers.IO) {
                    val article = event.article
                    val record = newsUseCases.selectArticle(url = article.url)
                    if (record == null) {
                        upsertArticle(article)
                    } else {
                        deleteArticle(article)
                    }
                }

                is DetailsEvent.RemoveSideEffectEvent -> {
                    _sideEffect = null
                }
            }
        }
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        _sideEffect?.emit("Article Saved")

    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        _sideEffect?.emit("Article Deleted")
    }

}
