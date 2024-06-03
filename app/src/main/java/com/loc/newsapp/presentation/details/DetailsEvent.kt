package com.loc.newsapp.presentation.details

import com.loc.newsapp.domain.model.Article

sealed class DetailsEvent {
    data class DeleteUpsertArticleEvent(val article : Article) : DetailsEvent()
    object RemoveSideEffectEvent : DetailsEvent()
}