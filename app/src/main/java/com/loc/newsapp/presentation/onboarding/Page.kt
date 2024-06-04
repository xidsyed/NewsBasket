package com.loc.newsapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.loc.newsapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf(
    Page(
        title = "Wherever Whenever!",
        description = "Breaking news from all over the world, that goes wherever you go",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Find What You Are Looking For!",
        description = "Search all your favorite international news sources in one place",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Save Your Favorites",
        description = "Bookmark all the articles that you wish to read later. Come back to them after a long day",
        image = R.drawable.onboarding3
    )
)