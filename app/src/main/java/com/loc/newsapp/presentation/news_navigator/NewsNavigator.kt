package com.loc.newsapp.presentation.news_navigator

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.data.local.bookmark.BookmarkScreen
import com.loc.newsapp.data.local.bookmark.BookmarkViewModel
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.details.DetailsEvent
import com.loc.newsapp.presentation.details.DetailsScreen
import com.loc.newsapp.presentation.details.DetailsViewModel
import com.loc.newsapp.presentation.home.HomeScreen
import com.loc.newsapp.presentation.home.HomeViewModel
import com.loc.newsapp.presentation.navgraph.Route
import com.loc.newsapp.presentation.news_navigator.components.BottomNavigationItem
import com.loc.newsapp.presentation.news_navigator.components.NewsBottomNavigation
import com.loc.newsapp.presentation.search.SearchScreen
import com.loc.newsapp.presentation.search.SearchViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsNavigator() {
    val bottomNavigationItems = listOf(
        BottomNavigationItem(
            icon = R.drawable.ic_home,
            label = "Home",
            destination = Route.HomeScreen
        ),
        BottomNavigationItem(
            icon = R.drawable.ic_search,
            label = "Search",
            destination = Route.SearchScreen
        ),
        BottomNavigationItem(
            icon = R.drawable.ic_bookmark,
            label = "Bookmark",
            destination = Route.BookmarkScreen
        )
    )

    val navController = rememberNavController()
    var selectedState by rememberSaveable { mutableIntStateOf(0) }
    val backStackEntry = navController.currentBackStackEntryAsState().value

    selectedState = remember(backStackEntry) {
        bottomNavigationItems.indexOfFirst {
            backStackEntry?.destination?.route == it.destination.route
        }
    }

    val isBottomBarVisible = remember(backStackEntry) {
        backStackEntry?.destination?.route == Route.HomeScreen.route ||
                backStackEntry?.destination?.route == Route.SearchScreen.route ||
                backStackEntry?.destination?.route == Route.BookmarkScreen.route
    }


    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                NewsBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedState,
                    onClick = { index ->
                        val route = bottomNavigationItems[index].destination.route
                        navigateToTab(navController, route)
                    }
                )
            }
        }
    ) { it ->
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val articles = viewModel.news.collectAsLazyPagingItems()

                HomeScreen(
                    articles = articles,
                    navigateToSearch = { navigateToTab(navController, Route.SearchScreen.route) },
                    navigateToDetails = { navigateToDetails(navController, it) }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                SearchScreen(
                    state = state,
                    onSearchEvent = viewModel::onSearchEvent,
                    navigateToDetails = { article ->
                        navigateToDetails(navController = navController, article = article)
                    })
            }

            composable(route = Route.DetailsScreen.route) {
                val article =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")
                article?.let {
                    Log.d("TAG", "NewsNavigator: $it")
                    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                }
                val viewModel: DetailsViewModel = hiltViewModel()

                val sideEffect = viewModel.sideEffect?.collectAsState()?.value
                val isBookmarked = viewModel.isBookmarked.collectAsState().value
                sideEffect?.let { text ->
                    Toast.makeText(LocalContext.current, text, LENGTH_SHORT).show()
                    viewModel.onDetailsEvent(DetailsEvent.RemoveSideEffectEvent)
                }

                article?.let {
                    DetailsScreen(
                        isBookmarked = isBookmarked,
                        article = it,
                        detailsEvent = viewModel::onDetailsEvent,
                        navigateUp = { navController.navigateUp() })
                }
            }

            composable(route = Route.BookmarkScreen.route) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                BookmarkScreen(state = state, navigateToDetails = { article ->
                    navigateToDetails(navController = navController, article = article)
                })
            }
        }

    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) { saveState = true }
        }
        restoreState = true
        launchSingleTop = true
    }
}

private fun navigateToDetails(navController: NavController, article: Article) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(Route.DetailsScreen.route)
}