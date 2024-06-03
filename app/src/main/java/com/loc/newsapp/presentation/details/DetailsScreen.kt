package com.loc.newsapp.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.loc.newsapp.R
import com.loc.newsapp.domain.model.Article
import com.loc.newsapp.domain.model.Source
import com.loc.newsapp.presentation.Dimens.ArticleImageHeight
import com.loc.newsapp.presentation.Dimens.MediumPadding1
import com.loc.newsapp.presentation.details.components.DetailsTopBar
import com.loc.newsapp.ui.theme.NewsAppTheme


@Composable
fun DetailsScreen(
    article: Article,
    detailsEvent: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    isBookmarked: Boolean
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        DetailsTopBar(
            isBookmarked = isBookmarked,
            onBrowsingClick = {
                Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse(article.url)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
            },
            onShareClick = {
                Intent(Intent.ACTION_SEND).also { intent ->
                    intent.putExtra(Intent.EXTRA_TEXT, article.url)
                    intent.type = "text/plain"
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                    }
                }
            },
            onBookmarkClick = { detailsEvent(DetailsEvent.DeleteUpsertArticleEvent(article)) },
            onBackClick = navigateUp
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(
                start = MediumPadding1,
                top = MediumPadding1,
                end = MediumPadding1,
            )
        ) {
            item {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(article.urlToImage).build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ArticleImageHeight)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(MediumPadding1))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.displaySmall,
                    color = colorResource(
                        R.color.text_title
                    )
                )

                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(
                        R.color.body
                    )
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun DetailsScreenPreview() {
    NewsAppTheme(dynamicColor = false) {
        val article = Article(
            author = "",
            title = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
            description = "Coinbase says Apple blocked its last app release on NFTs in Wallet ... - CryptoSaurus",
            content = "We use cookies and data to Deliver and maintain Google services Track outages and protect against spam, fraud, and abuse Measure audience engagement and site statistics to unde… [+1131 chars]",
            publishedAt = "2023-06-16T22:24:33Z",
            source = Source(
                id = "", name = "bbc"
            ),
            url = "https://consent.google.com/ml?continue=https://news.google.com/rss/articles/CBMiaWh0dHBzOi8vY3J5cHRvc2F1cnVzLnRlY2gvY29pbmJhc2Utc2F5cy1hcHBsZS1ibG9ja2VkLWl0cy1sYXN0LWFwcC1yZWxlYXNlLW9uLW5mdHMtaW4td2FsbGV0LXJldXRlcnMtY29tL9IBAA?oc%3D5&gl=FR&hl=en-US&cm=2&pc=n&src=1",
            urlToImage = "https://media.wired.com/photos/6495d5e893ba5cd8bbdc95af/191:100/w_1280,c_limit/The-EU-Rules-Phone-Batteries-Must-Be-Replaceable-Gear-2BE6PRN.jpg"
        )
        DetailsScreen(article = article, detailsEvent = {}, navigateUp = {}, isBookmarked = true)
    }
}
