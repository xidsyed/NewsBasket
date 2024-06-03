package com.loc.newsapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.loc.newsapp.domain.usecases.news.NewsUseCases
import com.loc.newsapp.util.Constants.SOURCES
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val newsUseCases: NewsUseCases) : ViewModel() {

    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun onSearchEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.update { state -> state.copy(searchQuery = event.query) }
            }

            is SearchEvent.SearchNews -> {
                val articlesFlow = newsUseCases.searchNews(state.value.searchQuery, SOURCES)
                    .cachedIn(viewModelScope)
                _state.update { it.copy(articlesFlow =  articlesFlow) }
            }
        }
    }

}