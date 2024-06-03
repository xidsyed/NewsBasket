package com.loc.newsapp.presentation.search

sealed class SearchEvent {
    class UpdateSearchQuery(val query : String) : SearchEvent()
    object SearchNews : SearchEvent() // TODO : Implement a search de-bouncer
}