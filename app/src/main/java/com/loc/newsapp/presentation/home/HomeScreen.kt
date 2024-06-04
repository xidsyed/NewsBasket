package com.loc.newsapp.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.presentation.Dimens.ExtraSmallPadding2
import com.loc.newsapp.presentation.Dimens.MediumPadding1
import com.loc.newsapp.presentation.common.ArticlesList
import com.loc.newsapp.presentation.common.SearchBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    articles: LazyPagingItems<Article>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Article) -> Unit
) {
    val headlines by remember {
        derivedStateOf {
            if (articles.itemCount >= 10)
                articles.itemSnapshotList.items
                    .slice(0..9).joinToString(" \uD83D\uDFE5 ") { it.title }
            else ""
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding1)
            .statusBarsPadding()
    ) {

        // Logo
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(horizontal = MediumPadding1)

        )

        Spacer(modifier = Modifier.padding(ExtraSmallPadding2))


        // Search Box
        SearchBar(
            text = "",
            modifier = Modifier
                .padding(horizontal = MediumPadding1)
                .fillMaxWidth(),
            onSearch = { },
            onClick = navigateToSearch,
            onValueChange = {},
            readOnly = true,

            )

        // Marquee
        Text(
            text = headlines,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding1, vertical = ExtraSmallPadding2)
                .basicMarquee(),
            fontSize = 12.sp,
            color = colorResource(id = R.color.placeholder)
        )


        // Article List
        ArticlesList(
            modifier = Modifier.padding(
                horizontal = MediumPadding1, vertical =
                ExtraSmallPadding2
            ),
            articles = articles,
            onClick = navigateToDetails
        )

    }


}

