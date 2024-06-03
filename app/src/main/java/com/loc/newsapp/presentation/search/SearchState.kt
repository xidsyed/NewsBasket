package com.loc.newsapp.presentation.search

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.loc.newsapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

@Immutable
data class SearchState(
    val searchQuery: String = "",
    val articlesFlow: Flow<PagingData<Article>>? = null
)

 